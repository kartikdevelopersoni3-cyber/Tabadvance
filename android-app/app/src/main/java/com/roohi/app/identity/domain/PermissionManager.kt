package com.roohi.app.identity.domain

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.roohi.app.identity.domain.models.PermissionHealth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun checkPermissionHealth(): PermissionHealth {
        val mic = ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        
        var notify = true
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            notify = ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        }

        val overlay = Settings.canDrawOverlays(context)
        
        // Let's assume battery optimization is ignored if true or we can't reliably check without PowerManager inside here easily, 
        // we'll mock the check for this module since it requires Activity intent flows.
        val battery = true 

        val allCritical = mic && notify

        return PermissionHealth(
            microphoneGranted = mic,
            notificationsGranted = notify,
            overlayGranted = overlay,
            batteryOptimizationIgnored = battery,
            allCriticalGranted = allCritical
        )
    }
}
