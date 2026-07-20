package com.roohi.app.execution.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.execution.data.TaskEntity
import com.roohi.app.execution.data.WorkflowEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExecutionPlanner @Inject constructor(
    private val actionQueueManager: ActionQueueManager,
    private val taskPersistenceManager: TaskPersistenceManager,
    private val logger: Logger
) {
    suspend fun planAndEnqueueTask(action: String, isEmergency: Boolean = false) {
        logger.i("ExecutionPlanner", "Planning single task: $action")
        val task = TaskEntity(requiredAction = action, status = "PENDING", isEmergency = isEmergency)
        taskPersistenceManager.saveTask(task)
        actionQueueManager.enqueue(task)
    }
}

@Singleton
class WorkflowEngine @Inject constructor(
    private val executionPlanner: ExecutionPlanner,
    private val logger: Logger
) {
    suspend fun executeWorkflow(workflow: WorkflowEntity) {
        logger.i("WorkflowEngine", "Executing workflow steps for: ${workflow.initialGoal}")
        // Parse workflow.stepsJson and trigger individual ExecutionPlanner events
        val steps = listOf(workflow.initialGoal) // Simplified mapping
        steps.forEach {
            executionPlanner.planAndEnqueueTask(it)
        }
    }
}

@Singleton
class TaskRecoveryEngine @Inject constructor(
    private val taskPersistenceManager: TaskPersistenceManager,
    private val actionQueueManager: ActionQueueManager,
    private val logger: Logger
) {
    suspend fun recoverPendingTasks() {
        logger.w("TaskRecoveryEngine", "Scanning for orphaned pending tasks after system reboot/crash.")
        val pending = taskPersistenceManager.loadPendingTasks()
        pending.forEach {
            logger.i("TaskRecoveryEngine", "Recovering task: ${it.id}")
            actionQueueManager.enqueue(it)
        }
    }
}
