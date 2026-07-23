package com.roohi.app.device.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
class EmergencyModeManager @Inject constructor(
    private val powerOptimizationManager: PowerOptimizationManager,
    private val logger: Logger
) {
    val isEmergencyMode = MutableStateFlow(false)

    fun triggerEmergency() {
        logger.w("EmergencyModeManager", "System bound to Emergency Protocol")
        isEmergencyMode.value = true
        powerOptimizationManager.lockPowerToEssential()
    }
    
    fun clearEmergency() {
        logger.i("EmergencyModeManager", "Emergency cleared.")
        isEmergencyMode.value = false
        powerOptimizationManager.restorePowerProfile()
    }
}

@Singleton
class AutomationManager @Inject constructor(
    private val deviceControlManager: DeviceControlManager,
    private val logger: Logger
) {
    fun executeAutomationMacro(macroId: String) {
        logger.i("AutomationManager", "Executing automation macro: $macroId")
    }
}

@Singleton
class DeviceStateManager @Inject constructor(
    private val logger: Logger
) {
    val isScreenLocked = MutableStateFlow(false)
    val isScreenOn = MutableStateFlow(true)
    
    fun notifyScreenStateChange(on: Boolean) {
        isScreenOn.value = on
        logger.d("DeviceStateManager", "Screen is now ${if(on) "ON" else "OFF"}")
    }
}

@Singleton
class PowerOptimizationManager @Inject constructor(
    private val logger: Logger
) {
    fun lockPowerToEssential() {
        logger.w("PowerOptimizationManager", "Throttling all background syncing - Essential only.")
    }
    fun restorePowerProfile() {
        logger.d("PowerOptimizationManager", "Restoring normal background power heuristics.")
    }
}
