package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.reasoning.domain.models.Goal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriorityEngine @Inject constructor(
    private val logger: Logger
) {
    fun rankGoals(goals: List<Goal>): List<Goal> {
        logger.i("PriorityEngine", "Ranking ${goals.size} goals.")
        return goals.sortedByDescending { goal ->
            var p = goal.priority
            if (goal.description.contains("emergency", ignoreCase = true)) p += 100
            p
        }
    }
}
