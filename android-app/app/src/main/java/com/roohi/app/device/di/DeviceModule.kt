package com.roohi.app.device.di

import android.content.Context
import com.roohi.app.core.logging.Logger
import com.roohi.app.device.domain.AccessibilityIntegrationManager
import com.roohi.app.device.domain.AppSessionManager
import com.roohi.app.device.domain.AutomationManager
import com.roohi.app.device.domain.DeviceControlManager
import com.roohi.app.device.domain.DeviceStateManager
import com.roohi.app.device.domain.EmergencyModeManager
import com.roohi.app.device.domain.FocusModeManager
import com.roohi.app.device.domain.NotificationOrchestrator
import com.roohi.app.device.domain.OverlayManager
import com.roohi.app.device.domain.PowerOptimizationManager
import com.roohi.app.device.domain.SystemSettingsManager
import com.roohi.app.device.monitor.AccessibilityValidator
import com.roohi.app.device.monitor.AutomationWatchdog
import com.roohi.app.device.monitor.DeviceDiagnosticsManager
import com.roohi.app.device.monitor.DeviceHealthMonitor
import com.roohi.app.device.monitor.EmergencyRecoveryEngine
import com.roohi.app.device.monitor.OverlayValidator
import com.roohi.app.device.monitor.PermissionRecoveryManager
import com.roohi.app.device.monitor.PermissionWatchdog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceModule {
    // Relying on bounded @Inject constructor definitions for singletons without custom factories.
}
