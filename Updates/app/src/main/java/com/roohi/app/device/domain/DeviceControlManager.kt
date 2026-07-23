package com.roohi.app.device.domain

import android.content.Context
import com.roohi.app.core.logging.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceControlManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSessionManager: AppSessionManager,
    private val focusModeManager: FocusModeManager,
    private val systemSettingsManager: SystemSettingsManager,
    private val logger: Logger
) {
    fun openApp(packageName: String): Boolean {
        logger.i("DeviceControlManager", "Requesting launch of $packageName")
        return appSessionManager.launchPackage(packageName)
    }
    
    fun toggleFocusMode(enable: Boolean) {
        logger.i("DeviceControlManager", "Toggling Focus Mode: $enable")
        focusModeManager.setFocusMode(enable)
    }
    
    fun adjustSetting(settingKey: String, value: String) {
        logger.i("DeviceControlManager", "Adjusting $settingKey to $value")
        systemSettingsManager.modifySetting(settingKey, value)
    }
}
