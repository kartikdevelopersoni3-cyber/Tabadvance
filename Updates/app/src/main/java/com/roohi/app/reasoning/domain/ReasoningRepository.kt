package com.roohi.app.reasoning.domain

import com.roohi.app.reasoning.domain.models.Goal
import com.roohi.app.reasoning.domain.models.Plan

interface ReasoningRepository {
    suspend fun saveGoal(goal: Goal)
    suspend fun getActiveGoals(): List<Goal>
    suspend fun getGoal(id: String): Goal?
    suspend fun savePlan(plan: Plan)
    suspend fun getPlansForGoal(goalId: String): List<Plan>
}
