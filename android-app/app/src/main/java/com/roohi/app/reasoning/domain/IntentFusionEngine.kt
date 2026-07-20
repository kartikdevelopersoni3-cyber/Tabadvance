package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.reasoning.domain.models.ReasoningContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntentFusionEngine @Inject constructor(
    private val logger: Logger
) {
    fun fuseContexts(query: String, context: ReasoningContext): String {
        logger.i("IntentFusionEngine", "Fusing conversation context with profile and memories.")
        return "FUSED_INTENT[$query|${context.currentIntent}]"
    }
}
