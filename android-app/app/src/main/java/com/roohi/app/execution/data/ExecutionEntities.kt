package com.roohi.app.execution.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val requiredAction: String,
    val status: String,
    val isEmergency: Boolean,
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "workflows")
data class WorkflowEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val initialGoal: String,
    val currentStepIndex: Int,
    val stepsJson: String,
    val status: String,
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "execution_history")
data class ExecutionHistoryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val taskId: String,
    val resultStatus: String,
    val responsePayload: String,
    val executionTimestamp: Long = System.currentTimeMillis()
)
