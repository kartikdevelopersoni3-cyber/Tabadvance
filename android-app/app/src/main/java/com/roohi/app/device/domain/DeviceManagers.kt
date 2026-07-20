package com.roohi.app.device.domain

import android.content.Context
import android.content.Intent
import com.roohi.app.core.logging.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class AppSessionManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) {
    private val _activeSession = MutableStateFlow<String?>(null)
    val activeSession: StateFlow<String?> = _activeSession

    fun launchPackage(packageName: String): Boolean {
        logger.i("AppSessionManager", "Attempting to launch package: $packageName")
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                _activeSession.value = packageName
                true
            } else {
                logger.e("AppSessionManager", "Package not found: $packageName")
                false
            }
        } catch (e: Exception) {
            logger.e("AppSessionManager", "Execution failure: ${e.message}")
            false
        }
    }
}

@Singleton
class FocusModeManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) {
    private val _focusModeActive = MutableStateFlow(false)
    val focusModeActive: StateFlow<Boolean> = _focusModeActive

    fun setFocusMode(enable: Boolean) {
        _focusModeActive.value = enable
        logger.d("FocusModeManager", "Focus Mode is now ${if(enable) "ON" else "OFF"}")
        // Integrate with DND settings / Notification orchestration later
    }
}

@Singleton
class SystemSettingsManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) {
    fun modifySetting(settingKey: String, value: String) {
        logger.d("SystemSettingsManager", "Pretending to write generic setting $settingKey -> $value")
    }
}
