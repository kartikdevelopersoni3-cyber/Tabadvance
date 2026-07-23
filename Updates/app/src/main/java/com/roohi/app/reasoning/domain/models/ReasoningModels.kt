package com.roohi.app.reasoning.domain.models

data class Goal(
    val id: String,
    val description: String,
    val priority: Int,
    val status: GoalStatus,
    val createdAt: Long,
    val updatedAt: Long
)

enum class GoalStatus {
    ACTIVE,
    COMPLETED,
    ARCHIVED,
    FAILED,
    PAUSED
}

data class Plan(
    val id: String,
    val goalId: String,
    val steps: List<PlanStep>,
    val status: PlanStatus,
    val createdAt: Long
)

data class PlanStep(
    val id: String,
    val description: String,
    val status: StepStatus,
    val dependencies: List<String>
)

enum class StepStatus { PENDING, IN_PROGRESS, COMPLETED, FAILED }
enum class PlanStatus { ACTIVE, COMPLETED, FAILED }

data class ReasoningContext(
    val currentIntent: String,
    val relevantMemories: List<String>,
    val ownerPreferences: Map<String, String>,
    val deviceStatus: String
)

data class ReasoningSession(
    val sessionId: String,
    val activeGoals: List<Goal>,
    val lastActiveTime: Long
)

data class ReasoningDiagnostics(
    val activeGoalCount: Int,
    val activePlanCount: Int,
    val reasoningLatencyMs: Long,
    val reasoningHealth: String,
    val goalCompletionRate: Float,
    val reasoningConfidence: Float
)
