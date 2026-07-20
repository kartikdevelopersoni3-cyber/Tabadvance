package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReasoningHealthMonitor @Inject constructor(
    private val reasoningManager: ReasoningManager,
    private val logger: Logger
) {
    fun checkHealth(): Boolean {
        return reasoningManager.diagnostics.value.reasoningHealth == "HEALTHY"
    }

    fun reportRecovery() {
        logger.w("ReasoningHealthMonitor", "Triggering reasoning safe mode recovery...")
    }
}
