package com.roohi.app.execution.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.command.domain.CommandExecutionManager
import com.roohi.app.execution.data.TaskEntity
import com.roohi.app.speech.domain.TextToSpeechManager
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Singleton
class TaskManager @Inject constructor(
    private val actionQueueManager: ActionQueueManager,
    private val taskPersistenceManager: TaskPersistenceManager,
    private val executionHistoryManager: ExecutionHistoryManager,
    private val commandExecutionManager: CommandExecutionManager,
    private val rollbackEngine: RollbackEngine,
    private val approvalManager: ApprovalManager,
    private val textToSpeechManager: TextToSpeechManager,
    private val logger: Logger
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun executeNext() {
        scope.launch {
            val task = actionQueueManager.dequeue() ?: return@launch
            logger.i("TaskManager", "Executing task: ${task.requiredAction}")

            taskPersistenceManager.updateTaskStatus(task.id, "IN_PROGRESS")
            
            if (approvalManager.requiresApproval(task.requiredAction)) {
                val approved = approvalManager.requestApproval(task.requiredAction)
                if (!approved) {
                    logger.w("TaskManager", "Task denied by ApprovalManager")
                    taskPersistenceManager.updateTaskStatus(task.id, "DENIED")
                    executionHistoryManager.logExecution(task.id, "DENIED", "User denied approval")
                    textToSpeechManager.speak("I need your approval for that task.")
                    return@launch
                }
            }

            try {
                // Bridge to command execution
                val result = commandExecutionManager.executeCommand(task.requiredAction)
                val finalResult = result ?: "Success fallback"
                
                logger.i("TaskManager", "Task Success: ${task.id}")
                taskPersistenceManager.updateTaskStatus(task.id, "COMPLETED")
                executionHistoryManager.logExecution(task.id, "COMPLETED", finalResult)
                
                textToSpeechManager.speak("Task completed.")
            } catch (e: Exception) {
                logger.e("TaskManager", "Task Failure: ${task.id}")
                taskPersistenceManager.updateTaskStatus(task.id, "FAILED")
                executionHistoryManager.logExecution(task.id, "FAILED", e.message ?: "Unknown error")
                rollbackEngine.rollbackTask(task.id)
                textToSpeechManager.speak("I encountered an error executing the task.")
            }
        }
    }
}
