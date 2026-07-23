package com.roohi.app.execution.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.execution.data.ExecutionHistoryDao
import com.roohi.app.execution.data.ExecutionHistoryEntity
import com.roohi.app.execution.data.TaskDao
import com.roohi.app.execution.data.TaskEntity
import com.roohi.app.execution.data.WorkflowDao
import com.roohi.app.execution.data.WorkflowEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskPersistenceManager @Inject constructor(
    private val taskDao: TaskDao,
    private val workflowDao: WorkflowDao,
    private val logger: Logger
) {
    suspend fun saveTask(task: TaskEntity) {
        logger.d("TaskPersistenceManager", "Saving task: ${task.id}")
        taskDao.insertTask(task)
    }

    suspend fun loadPendingTasks(): List<TaskEntity> {
        return taskDao.getTasksByStatus("PENDING")
    }
    
    suspend fun updateTaskStatus(id: String, status: String) {
        taskDao.updateTaskStatus(id, status)
    }
}

@Singleton
class ExecutionHistoryManager @Inject constructor(
    private val executionHistoryDao: ExecutionHistoryDao,
    private val logger: Logger
) {
    suspend fun logExecution(taskId: String, status: String, result: String) {
        logger.d("ExecutionHistoryManager", "Logging execution for task: $taskId")
        executionHistoryDao.insertHistory(ExecutionHistoryEntity(
            taskId = taskId,
            resultStatus = status,
            responsePayload = result
        ))
    }
}
