package com.roohi.app.background.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.roohi.app.R
import com.roohi.app.core.logging.Logger
import com.roohi.app.wakeword.domain.WakeWordManager
import com.roohi.app.speech.domain.SpeechRecognitionManager
import com.roohi.app.conversation.domain.ConversationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
class RoohiBackgroundService : Service() {

    @Inject lateinit var logger: Logger
    @Inject lateinit var wakeWordManager: WakeWordManager
    @Inject lateinit var speechManager: SpeechRecognitionManager
    @Inject lateinit var conversationManager: ConversationManager
    @Inject lateinit var serviceHealthMonitor: com.roohi.app.background.monitor.ServiceHealthMonitor
    @Inject lateinit var foundationHealthMonitor: com.roohi.app.background.monitor.FoundationHealthMonitor

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var wakeWordJob: Job? = null
    private var speechObservationJob: Job? = null

    companion object {
        private const val CHANNEL_ID = "roohi_background_service_channel"
        private const val NOTIFICATION_ID = 1001
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onCreate() {
        super.onCreate()
        logger.i("RoohiBackgroundService", "Service Created")
        serviceHealthMonitor.reportSelfRecovery()
        foundationHealthMonitor.startMonitoring()
        createNotificationChannel()
        observeSpeechState()
    }

    private fun observeSpeechState() {
        speechObservationJob = serviceScope.launch {
            speechManager.speechState.collectLatest { result ->
                if (result != null) {
                    if (!result.isPartial && result.error == null && result.transcript.isNotBlank()) {
                        logger.i("RoohiBackgroundService", "Final Speech Received: \${result.transcript}")
                        conversationManager.handleUserInput(result.transcript)
                        
                        // Resume listening for the next wake word
                        startWakeWordEngine()
                    } else if (result.error != null) {
                        logger.w("RoohiBackgroundService", "Speech Error: \${result.error}. Resuming Wake Word Engine.")
                        startWakeWordEngine()
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        logger.i("RoohiBackgroundService", "onStartCommand action: $action")

        when (action) {
            ACTION_START -> {
                startForegroundService()
                startWakeWordEngine()
            }
            ACTION_STOP -> stopForegroundService()
            else -> {
                startForegroundService()
                startWakeWordEngine()
            }
        }

        return START_STICKY
    }

    private fun startWakeWordEngine() {
        if (wakeWordJob?.isActive == true) return
        wakeWordJob = serviceScope.launch {
            logger.i("RoohiBackgroundService", "Delegating to WakeWordManager to begin continuous MIC capture...")
            wakeWordManager.startListening()
        }
    }

    private fun startForegroundService() {
        logger.d("RoohiBackgroundService", "Starting foreground service")
        val notification = createNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIFICATION_ID, 
                notification, 
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE or android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID, 
                notification, 
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun stopForegroundService() {
        logger.d("RoohiBackgroundService", "Stopping foreground service")
        wakeWordManager.stop()
        wakeWordJob?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Roohi Background Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps Roohi active in the background"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Roohi is listening")
            .setContentText("Background AI service and wake word engine active")
            .setSmallIcon(R.mipmap.ic_launcher) // Update with actual icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.w("RoohiBackgroundService", "Service Destroyed")
        wakeWordManager.stop()
        serviceScope.cancel()
        conversationManager.endConversation()
        
        // Broadcast intent to restart the service (auto restart logic)
        val restartIntent = Intent(this, com.roohi.app.background.receiver.ServiceRestartReceiver::class.java)
        restartIntent.action = "com.roohi.app.RESTART_SERVICE"
        sendBroadcast(restartIntent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // We don't provide binding
    }
}
