package com.roohi.app.automation.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.automation.data.AutomationDao
import com.roohi.app.automation.data.WorkflowEntity
import com.roohi.app.automation.data.ExecutionHistoryEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class WorkflowRepository @Inject constructor(
    private val automationDao: AutomationDao,
    private val logger: Logger
) {
    suspend fun saveWorkflow(workflow: WorkflowEntity) {
        automationDao.insertWorkflow(workflow)
        logger.i("WorkflowRepository", "Workflow saved: ${workflow.name}")
    }

    suspend fun getActiveWorkflows(): List<WorkflowEntity> {
        return automationDao.getActiveWorkflows()
    }
    
    suspend fun logExecution(workflowId: String, success: Boolean, reason: String? = null) {
        automationDao.insertExecutionHistory(ExecutionHistoryEntity(workflowId = workflowId, success = success, failureReason = reason))
    }
}

@Singleton
class AutomationTemplateManager @Inject constructor(private val logger: Logger) {
    fun getTemplate(templateName: String): String {
        logger.d("AutomationTemplateManager", "Retrieving template: $templateName")
        return "Template_$templateName"
    }
}

@Singleton
class WorkflowManager @Inject constructor(
    private val workflowBuilder: WorkflowBuilder,
    private val workflowRepository: WorkflowRepository,
    private val workflowExecutor: WorkflowExecutor,
    private val triggerEngine: TriggerEngine,
    private val logger: Logger
) {
    suspend fun createVoiceAutomation(command: String) {
        val payload = workflowBuilder.buildFromVoiceCommand(command)
        val entity = WorkflowEntity(
            name = "Voice Automation",
            triggerCondition = "VoiceCommand",
            actionPayload = payload
        )
        workflowRepository.saveWorkflow(entity)
    }
    
    suspend fun triggerCheck(systemState: String) {
        val workflows = workflowRepository.getActiveWorkflows()
        for (w in workflows) {
            if (triggerEngine.evaluateTrigger(w.triggerCondition, systemState)) {
                workflowExecutor.execute(w.id, w.actionPayload)
                workflowRepository.logExecution(w.id, true)
            }
        }
    }
}

@Singleton
class AutomationManager @Inject constructor(
    private val workflowManager: WorkflowManager,
    private val workflowScheduler: WorkflowScheduler,
    private val recoveryWorkflowEngine: RecoveryWorkflowEngine,
    private val logger: Logger
) {
    suspend fun handleVoiceRequest(request: String) {
        logger.i("AutomationManager", "Handling voice automation request: $request")
        workflowManager.createVoiceAutomation(request)
    }
}
