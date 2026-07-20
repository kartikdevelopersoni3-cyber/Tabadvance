package com.roohi.app.background.monitor

import android.app.ActivityManager
import android.content.Context
import com.roohi.app.background.service.RoohiBackgroundService
import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceHealthMonitor @Inject constructor(
    private val context: Context,
    private val logger: Logger
) {

    @Suppress("DEPRECATION")
    fun isServiceRunning(): Boolean {
        var isRunning = false
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (RoohiBackgroundService::class.java.name == service.service.className) {
                isRunning = true
                break
            }
        }
        
        if (isRunning) {
            logger.d("ServiceHealthMonitor", "RoohiBackgroundService is currently RUNNING.")
        } else {
            logger.w("ServiceHealthMonitor", "RoohiBackgroundService is NOT RUNNING.")
        }
        return isRunning
    }
}
