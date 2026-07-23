package com.roohi.app.coordination.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class AgentMessageBus @Inject constructor(
    private val logger: Logger
) {
    private val _messages = MutableSharedFlow<AgentMessage>(replay = 10)
    val messages: SharedFlow<AgentMessage> = _messages.asSharedFlow()

    suspend fun dispatch(message: AgentMessage) {
        logger.d("AgentMessageBus", "Dispatching message from ${message.senderId} to ${message.targetId ?: "BROADCAST"}: ${message.payload}")
        _messages.emit(message)
    }
}
