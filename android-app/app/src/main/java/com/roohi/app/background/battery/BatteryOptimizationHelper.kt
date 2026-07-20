package com.roohi.app.background.battery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BatteryOptimizationHelper @Inject constructor() {

    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            powerManager.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            true // Battery optimization feature was introduced in Marshmallow (API 23)
        }
    }

    fun getBatteryOptimizationIntent(context: Context): Intent? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isIgnoringBatteryOptimizations(context)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:${context.packageName}")
            return intent
        }
        return null
    }

    fun requestIgnoreBatteryOptimizations(context: Context, logger: Logger) {
        val intent = getBatteryOptimizationIntent(context)
        if (intent != null) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                context.startActivity(intent)
                logger.i("BatteryOptimizationHelper", "Requested battery optimization ignore.")
            } catch (e: Exception) {
                logger.e("BatteryOptimizationHelper", "Error requesting battery optimization ignore", e)
            }
        } else {
            logger.i("BatteryOptimizationHelper", "Battery optimization is already ignored or SDK < 23.")
        }
    }
}
