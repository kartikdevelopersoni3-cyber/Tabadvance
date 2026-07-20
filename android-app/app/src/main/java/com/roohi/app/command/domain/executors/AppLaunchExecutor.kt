package com.roohi.app.command.domain.executors

import android.content.Context
import android.content.Intent
import android.content.ActivityNotFoundException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLaunchExecutor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute(appName: String): Pair<Boolean, String?> {
        val packageName = getPackageNameForApp(appName)
        if (packageName == null) {
            return Pair(false, "App '$appName' not found or supported.")
        }
        
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                context.startActivity(intent)
                Pair(true, null)
            } else {
                Pair(false, "App is not installed on this device.")
            }
        } catch (e: SecurityException) {
            Pair(false, "Security permission denied to launch app.")
        } catch (e: ActivityNotFoundException) {
            Pair(false, "Activity not found to handle launch.")
        } catch (e: Exception) {
            Pair(false, "Failed to launch app: ${e.message}")
        }
    }

    private fun getPackageNameForApp(appName: String): String? {
        return when (appName.lowercase()) {
            "youtube" -> "com.google.android.youtube"
            "chrome" -> "com.android.chrome"
            "calculator" -> "com.google.android.calculator"
            "clock" -> "com.google.android.deskclock"
            "camera" -> "com.google.android.GoogleCamera"
            "settings" -> "com.android.settings"
            else -> null
        }
    }
}
