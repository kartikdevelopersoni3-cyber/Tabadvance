package com.roohi.app.coordination.domain

data class AgentMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val senderId: String,
    val targetId: String? = null,
    val payload: String,
    val requiresResponse: Boolean = false,
    val priority: Int = 1
)

enum class AgentStatus {
    IDLE,
    WORKING,
    ERROR,
    DEAD
}
