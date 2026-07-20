package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.reasoning.domain.models.PlanStep
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionPlanner @Inject constructor(
    private val logger: Logger
) {
    fun prepareExecutionStrategy(step: PlanStep): String {
        logger.i("ActionPlanner", "Prepared action strategy for step: ${step.description}")
        return "ROOHI_ACTION_COMMAND: ${step.description}"
    }
}
