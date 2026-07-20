package com.roohi.app.coordination.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.coordination.agents.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Singleton
class AgentCoordinator @Inject constructor(
    private val agentRegistry: AgentRegistry,
    private val messageBus: AgentMessageBus,
    private val plannerAgent: PlannerAgent,
    private val memoryAgent: MemoryAgent,
    private val deviceAgent: DeviceAgent,
    private val personalityAgent: PersonalityAgent,
    private val executionAgent: ExecutionAgent,
    private val visionAgent: VisionAgent,
    private val automationAgent: AutomationAgent,
    private val learningAgent: LearningAgent,
    private val knowledgeAgent: KnowledgeAgent,
    private val proactiveAgent: ProactiveAgent,
    private val workspaceAgent: WorkspaceAgent,
    private val logger: Logger
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        // Bootstrap agent registrations
        agentRegistry.register(plannerAgent)
        agentRegistry.register(memoryAgent)
        agentRegistry.register(deviceAgent)
        agentRegistry.register(personalityAgent)
        agentRegistry.register(executionAgent)
        agentRegistry.register(visionAgent)
        agentRegistry.register(automationAgent)
        agentRegistry.register(learningAgent)
        agentRegistry.register(knowledgeAgent)
        agentRegistry.register(proactiveAgent)
        agentRegistry.register(workspaceAgent)
        
        startListening()
    }

    private fun startListening() {
        scope.launch {
            messageBus.messages.collectLatest { message ->
                logger.d("AgentCoordinator", "Routing message from ${message.senderId} to ${message.targetId}")
                if (message.targetId != null) {
                    val target = agentRegistry.getAgent(message.targetId)
                    target?.handleMessage(message)
                } else {
                    // Broadcast
                    agentRegistry.getAllAgents().forEach { agent ->
                        if (agent.agentId != message.senderId) {
                            agent.handleMessage(message)
                        }
                    }
                }
            }
        }
    }
    
    suspend fun coordinateTask(goal: String) {
        logger.i("AgentCoordinator", "Coordinating multi-agent logic for goal: $goal")
        // Initial routing triggers the Planner Agent
        messageBus.dispatch(AgentMessage(
            senderId = "Coordinator",
            targetId = "PlannerAgent",
            payload = goal
        ))
    }
}
