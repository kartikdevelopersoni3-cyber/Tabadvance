package com.roohi.app.execution.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.execution.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionQueueManager @Inject constructor(
    private val logger: Logger
) {
    private val _queue = MutableStateFlow<List<TaskEntity>>(emptyList())
    val queue: StateFlow<List<TaskEntity>> = _queue.asStateFlow()

    fun enqueue(task: TaskEntity) {
        logger.d("ActionQueueManager", "Enqueuing task: ${task.id}")
        _queue.value = _queue.value + task
    }

    fun dequeue(): TaskEntity? {
        val currentQueue = _queue.value
        if (currentQueue.isEmpty()) return null
        val task = currentQueue.first()
        _queue.value = currentQueue.drop(1)
        return task
    }
    
    fun clear() {
        _queue.value = emptyList()
    }
}

@Singleton
class ApprovalManager @Inject constructor(
    private val logger: Logger
) {
    fun requiresApproval(action: String): Boolean {
        // e.g., "Delete contacts" -> true
        val safeActions = listOf("Open", "Read", "Query", "Check")
        return !safeActions.any { action.startsWith(it, ignoreCase = true) }
    }

    suspend fun requestApproval(action: String): Boolean {
        logger.w("ApprovalManager", "Seeking approval for risky action: $action")
        // Stub: Assume approved
        return true
    }
}

@Singleton
class RollbackEngine @Inject constructor(
    private val executionHistoryManager: ExecutionHistoryManager,
    private val logger: Logger
) {
    suspend fun rollbackTask(taskId: String) {
        logger.e("RollbackEngine", "Attempting rollback for task: $taskId")
        executionHistoryManager.logExecution(taskId, "ROLLED_BACK", "Execution reversed.")
    }
}

@Singleton
class TaskScheduler @Inject constructor(
    private val actionQueueManager: ActionQueueManager,
    private val logger: Logger
) {
    fun schedule(task: TaskEntity, delayMs: Long) {
        logger.i("TaskScheduler", "Scheduling task ${task.id} with delay $delayMs")
        // In real execution, would use WorkManager or Coroutine delay.
        // For now, enqueue directly
        actionQueueManager.enqueue(task)
    }
}
