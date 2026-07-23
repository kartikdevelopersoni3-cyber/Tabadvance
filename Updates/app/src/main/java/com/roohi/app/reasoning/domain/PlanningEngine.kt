package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.reasoning.domain.models.Goal
import com.roohi.app.reasoning.domain.models.Plan
import com.roohi.app.reasoning.domain.models.PlanStatus
import com.roohi.app.reasoning.domain.models.PlanStep
import com.roohi.app.reasoning.domain.models.StepStatus
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanningEngine @Inject constructor(
    private val repository: ReasoningRepository,
    private val logger: Logger
) {
    suspend fun createPlanForGoal(goal: Goal, stepsInput: List<String>): Plan {
        val steps = stepsInput.mapIndexed { index, desc ->
            val deps = if (index == 0) emptyList() else listOf("${goal.id}_step_${index - 1}")
            PlanStep("${goal.id}_step_$index", desc, StepStatus.PENDING, deps)
        }
        val plan = Plan(UUID.randomUUID().toString(), goal.id, steps, PlanStatus.ACTIVE, System.currentTimeMillis())
        repository.savePlan(plan)
        logger.i("PlanningEngine", "Created plan for goal: ${goal.description} with ${steps.size} steps")
        return plan
    }
    
    suspend fun getPlans(goalId: String): List<Plan> = repository.getPlansForGoal(goalId)
}
