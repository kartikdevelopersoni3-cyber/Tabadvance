package com.roohi.app.coordination.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.coordination.agents.BaseAgent
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class AgentRegistry @Inject constructor(
    private val logger: Logger
) {
    private val agents = mutableMapOf<String, BaseAgent>()
    
    fun register(agent: BaseAgent) {
        agents[agent.agentId] = agent
        logger.d("AgentRegistry", "Registered agent: ${agent.agentId}")
    }
    
    fun getAgent(agentId: String): BaseAgent? = agents[agentId]
    
    fun getAllAgents(): List<BaseAgent> = agents.values.toList()
    
    fun removeAgent(agentId: String) {
        agents.remove(agentId)
        logger.w("AgentRegistry", "Unregistered agent: $agentId")
    }
}
