package com.roohi.app.background.monitor

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecoveryMonitor @Inject constructor(
    private val logger: Logger
) {
    fun triggerRecovery(component: String) {
         logger.w("RecoveryMonitor", "Attempting self-recovery for $component")
    }
}
