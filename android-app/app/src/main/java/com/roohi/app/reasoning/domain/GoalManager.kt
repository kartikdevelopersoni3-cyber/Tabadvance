package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.reasoning.domain.models.Goal
import com.roohi.app.reasoning.domain.models.GoalStatus
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalManager @Inject constructor(
    private val repository: ReasoningRepository,
    private val logger: Logger
) {
    suspend fun createGoal(description: String, priority: Int): Goal {
        val goal = Goal(UUID.randomUUID().toString(), description, priority, GoalStatus.ACTIVE, System.currentTimeMillis(), System.currentTimeMillis())
        repository.saveGoal(goal)
        logger.i("GoalManager", "Goal created: $description")
        return goal
    }

    suspend fun getActiveGoals(): List<Goal> = repository.getActiveGoals()

    suspend fun updateGoalStatus(goalId: String, status: GoalStatus) {
        val goal = repository.getGoal(goalId)
        if (goal != null) {
            repository.saveGoal(goal.copy(status = status, updatedAt = System.currentTimeMillis()))
            logger.i("GoalManager", "Goal $goalId status updated to $status")
        }
    }
}
