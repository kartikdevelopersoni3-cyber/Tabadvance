package com.roohi.app.background.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.roohi.app.background.service.RoohiBackgroundService
import com.roohi.app.core.logging.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ServiceRestartReceiver : BroadcastReceiver() {

    @Inject lateinit var logger: Logger

    override fun onReceive(context: Context, intent: Intent) {
        logger.w("ServiceRestartReceiver", "Received request to restart RoohiBackgroundService. Intent Action: ${intent.action}")
        
        val serviceIntent = Intent(context, RoohiBackgroundService::class.java).apply {
            action = RoohiBackgroundService.ACTION_START
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        } catch (e: Exception) {
            logger.e("ServiceRestartReceiver", "Failed to restart service", e)
        }
    }
}
