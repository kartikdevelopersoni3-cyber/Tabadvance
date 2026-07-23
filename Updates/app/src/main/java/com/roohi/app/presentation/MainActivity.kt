package com.roohi.app.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.roohi.app.R
import com.roohi.app.background.battery.BatteryOptimizationHelper
import com.roohi.app.core.logging.Logger
import com.roohi.app.core.ml.InterpreterProvider
import com.roohi.app.dsp.SampleRateManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.roohi.app.conversation.domain.ConversationManager
import com.roohi.app.conversation.domain.ContextManager
import com.roohi.app.command.domain.ExecutionResultManager
import com.roohi.app.memory.domain.MemoryManager
import com.roohi.app.identity.domain.SetupWizardManager

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var logger: Logger
    @Inject lateinit var batteryHelper: BatteryOptimizationHelper
    @Inject lateinit var interpreterProvider: InterpreterProvider
    @Inject lateinit var sampleRateManager: SampleRateManager
    @Inject lateinit var conversationManager: ConversationManager
    @Inject lateinit var contextManager: ContextManager
    @Inject lateinit var executionResultManager: ExecutionResultManager
    @Inject lateinit var memoryManager: MemoryManager
    @Inject lateinit var setupWizardManager: SetupWizardManager

    companion object {
        private const val PERMISSION_REQUEST_CODE = 404
        private val REQUIRED_PERMISSIONS = mutableListOf<String>().apply {
            add(Manifest.permission.RECORD_AUDIO)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }.toTypedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logger.i("MainActivity", "Main Activity created. Patch diagnostics initialized.")

        checkPermissions()
        refreshDiagnosticsUI()
    }

    private fun checkPermissions() {
        val missingPermissions = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missingPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        } else {
            onPermissionsGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                onPermissionsGranted()
            } else {
                logger.w("MainActivity", "Required permissions not granted. Cannot run AI modules.")
                refreshDiagnosticsUI()
            }
        }
    }

    private fun onPermissionsGranted() {
        checkAndRequestBatteryOptimization()
        startBackgroundService()
        refreshDiagnosticsUI()
    }

    private fun refreshDiagnosticsUI() {
        val tvNotification = findViewById<TextView>(R.id.tvNotificationStatus)
        val tvMic = findViewById<TextView>(R.id.tvMicStatus)
        val tvBattery = findViewById<TextView>(R.id.tvBatteryStatus)
        val tvWakeMod = findViewById<TextView>(R.id.tvWakeModelStatus)
        val tvAuthMod = findViewById<TextView>(R.id.tvAuthModelStatus)
        val tvDspStatus = findViewById<TextView>(R.id.tvDspStatus)
        val tvSpeechStatus = findViewById<TextView>(R.id.tvSpeechStatus)

        // Permission Checks
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notifGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            tvNotification.text = "Notification Permission: \${if (notifGranted) "GRANTED ✔" else "DENIED ❌"}"
        } else {
            tvNotification.text = "Notification Permission: GRANTED (API < 33)"
        }

        val micGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        tvMic.text = "Microphone Status: \${if (micGranted) "READY ✔" else "DENIED ❌"}"

        val batOpt = batteryHelper.isIgnoringBatteryOptimizations(this)
        tvBattery.text = "Battery Optimization: \${if (batOpt) "IGNORED ✔" else "RESTRICTED ❌"}"

        // Model Checks
        val assetMap = interpreterProvider.verifyAssets("roohi_wakeword.tflite", "speaker_dvector_model.tflite")
        val wakeReady = assetMap["roohi_wakeword.tflite"] == true
        val authReady = assetMap["speaker_dvector_model.tflite"] == true

        tvWakeMod.text = "Wake Word Model: \${if (wakeReady) "FOUND ✔" else "STUB MODE ⚠"}"
        tvAuthMod.text = "Voice Auth Model: \${if (authReady) "FOUND ✔" else "STUB MODE ⚠"}"

        val sampleRate = sampleRateManager.getOptimalSampleRate()
        tvDspStatus.text = "Audio DSP Pipeline: READY ✔ (\${sampleRate}Hz)"

        val speechAvailable = SpeechRecognizer.isRecognitionAvailable(this)
        tvSpeechStatus.text = "Speech Recognition: \${if (speechAvailable) "READY ✔" else "NOT AVAILABLE ❌"}"

        val tvConvActive = findViewById<TextView>(R.id.tvConvActive)
        val tvConvIntent = findViewById<TextView>(R.id.tvConvIntent)
        val tvConvContext = findViewById<TextView>(R.id.tvConvContext)
        val tvConvHealth = findViewById<TextView>(R.id.tvConvHealth)

        lifecycleScope.launch {
            conversationManager.isConversationActive.collectLatest { isActive ->
                tvConvActive.text = "Conversation Active: \${if (isActive) "YES 🟢" else "NO ⚪"}"
                tvConvHealth.text = "Conversation Health: \${if (isActive) "LISTENING..." else "SLEEPING"}"
            }
        }

        lifecycleScope.launch {
            conversationManager.currentIntent.collectLatest { intent ->
                tvConvIntent.text = "Current Intent: \${intent.name}"
                val ctx = contextManager.getContext()
                tvConvContext.text = "Context / Topic: \${ctx.currentTopic} (Msgs: \${ctx.messageCount})"
            }
        }

        val tvCommandStatus = findViewById<TextView>(R.id.tvCommandStatus)
        lifecycleScope.launch {
            executionResultManager.lastResult.collectLatest { result ->
                if (result != null) {
                    tvCommandStatus.text = "Cmd: \${result.command.type} | Valid: \${result.success}"
                } else {
                    tvCommandStatus.text = "Cmd: Waiting..."
                }
            }
        }

        val tvMemoryStatus = findViewById<TextView>(R.id.tvMemoryStatus)
        lifecycleScope.launch {
            memoryManager.memoryDiagnostics.collectLatest { diag ->
                tvMemoryStatus.text = "Memory: \${diag.totalCount} (ST: \${diag.shortTermCount}, LT: \${diag.longTermCount})"
            }
        }

        val tvIdentityStatus = findViewById<TextView>(R.id.tvIdentityStatus)
        lifecycleScope.launch {
            setupWizardManager.refreshDiagnostics()
            setupWizardManager.diagnostics.collectLatest { diag ->
                tvIdentityStatus.text = "Identity: Setup=\${diag.setupState} | Auth=\${diag.securityStatus} | Registered=\${diag.isOwnerRegistered}"
            }
        }
    }

    private fun checkAndRequestBatteryOptimization() {
        if (!batteryHelper.isIgnoringBatteryOptimizations(this)) {
            logger.i("MainActivity", "Requesting ignore battery optimizations")
            batteryHelper.requestIgnoreBatteryOptimizations(this, logger)
        }
    }

    private fun startBackgroundService() {
        logger.i("MainActivity", "Attempting to start RoohiBackgroundService")
        val serviceIntent = Intent(this, RoohiBackgroundService::class.java).apply {
            action = RoohiBackgroundService.ACTION_START
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        } catch (e: Exception) {
            logger.e("MainActivity", "Failed to start RoohiBackgroundService directly", e)
        }
    }
}
