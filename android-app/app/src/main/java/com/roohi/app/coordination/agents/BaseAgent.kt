package com.roohi.app.coordination.agents

import com.roohi.app.core.logging.Logger
import com.roohi.app.coordination.domain.AgentMessage
import com.roohi.app.coordination.domain.AgentMessageBus
import com.roohi.app.coordination.domain.AgentStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseAgent(
    val agentId: String,
    protected val messageBus: AgentMessageBus,
    protected val logger: Logger
) {
    protected val _status = MutableStateFlow(AgentStatus.IDLE)
    val status: StateFlow<AgentStatus> = _status

    open suspend fun handleMessage(message: AgentMessage) {
        logger.d(agentId, "Received message from ${message.senderId}")
    }

    protected suspend fun sendMessage(targetId: String?, payload: String) {
        messageBus.dispatch(AgentMessage(senderId = agentId, targetId = targetId, payload = payload))
    }
    
    fun setStatus(newStatus: AgentStatus) {
        _status.value = newStatus
        logger.d(agentId, "Status changed to $newStatus")
    }
}
