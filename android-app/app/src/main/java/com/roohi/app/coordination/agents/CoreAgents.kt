package com.roohi.app.coordination.agents

import com.roohi.app.core.logging.Logger
import com.roohi.app.coordination.domain.AgentMessage
import com.roohi.app.coordination.domain.AgentMessageBus
import com.roohi.app.coordination.domain.AgentStatus
import com.roohi.app.execution.domain.ExecutionPlanner
import com.roohi.app.reasoning.domain.MemoryReasoner
import com.roohi.app.device.domain.DeviceControlManager
import com.roohi.app.personality.domain.PersonalityManager
import javax.inject.Inject
import javax.inject.Singleton

import com.roohi.app.vision.domain.VisionManager

@Singleton
class PlannerAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val executionPlanner: ExecutionPlanner,
    logger: Logger
) : BaseAgent("PlannerAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Breaking down goal: ${message.payload}")
        // Placeholder for breakdown logic mapped to execution planner
        sendMessage(null, "Plan structured for ${message.payload}")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class MemoryAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val memoryReasoner: MemoryReasoner,
    logger: Logger
) : BaseAgent("MemoryAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Retrieving memory for: ${message.payload}")
        val memoryContext = memoryReasoner.analyzeMemory(message.payload)
        sendMessage(message.senderId, "MemoryContext: $memoryContext")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class DeviceAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val deviceControlManager: DeviceControlManager,
    logger: Logger
) : BaseAgent("DeviceAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Evaluating device operations for: ${message.payload}")
        // Integrates with Module C functions
        sendMessage(message.senderId, "Device State OK from Agent")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class PersonalityAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val personalityManager: PersonalityManager,
    logger: Logger
) : BaseAgent("PersonalityAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        val pc = personalityManager.getEmotionContext()
        sendMessage(message.senderId, "Personality Context Applied: $pc")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class ExecutionAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val executionPlanner: ExecutionPlanner,
    logger: Logger
) : BaseAgent("ExecutionAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Forwarding to Execution Planner: ${message.payload}")
        executionPlanner.planAndEnqueueTask(message.payload)
        sendMessage(message.senderId, "Action enqueued successfully via Planner")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class VisionAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val visionManager: VisionManager,
    logger: Logger
) : BaseAgent("VisionAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Evaluating visual context for: ${message.payload}")
        // Integrates with Module E Vision Engine
        visionManager.processVisualInput()
        sendMessage(message.senderId, "Vision State Extracted")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class AutomationAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val automationManager: com.roohi.app.automation.domain.AutomationManager,
    logger: Logger
) : BaseAgent("AutomationAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Evaluating automation voice command: ${message.payload}")
        automationManager.handleVoiceRequest(message.payload)
        sendMessage(message.senderId, "Automation Registered")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class LearningAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val learningManager: com.roohi.app.learning.domain.LearningManager,
    logger: Logger
) : BaseAgent("LearningAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Observing behavior for adaptation: ${message.payload}")
        learningManager.observeBehavior("AgentCoordinator", "GENERAL_BEHAVIOR", message.payload)
        sendMessage(message.senderId, "Behavior Observed")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class KnowledgeAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val knowledgeManager: com.roohi.app.knowledge.domain.KnowledgeManager,
    logger: Logger
) : BaseAgent("KnowledgeAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Accessing knowledge base for: ${message.payload}")
        val context = knowledgeManager.queryKnowledge(message.payload)
        sendMessage(message.senderId, "Knowledge Context: $context")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class ProactiveAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val proactiveManager: com.roohi.app.proactive.domain.ProactiveManager,
    logger: Logger
) : BaseAgent("ProactiveAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Evaluating proactive scenario for: ${message.payload}")
        val context = proactiveManager.handleDeviceStateChange(message.payload)
        sendMessage(message.senderId, "Proactive Scenario: $context")
        setStatus(AgentStatus.IDLE)
    }
}

@Singleton
class WorkspaceAgent @Inject constructor(
    messageBus: AgentMessageBus,
    private val workspaceManager: com.roohi.app.workspace.domain.WorkspaceManager,
    logger: Logger
) : BaseAgent("WorkspaceAgent", messageBus, logger) {
    override suspend fun handleMessage(message: AgentMessage) {
        setStatus(AgentStatus.WORKING)
        logger.i(agentId, "Processing workspace request: ${message.payload}")
        workspaceManager.switchToWorkspace(message.payload)
        sendMessage(message.senderId, "Workspace processed: ${message.payload}")
        setStatus(AgentStatus.IDLE)
    }
}
