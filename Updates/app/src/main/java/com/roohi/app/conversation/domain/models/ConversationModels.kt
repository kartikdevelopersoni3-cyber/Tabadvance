package com.roohi.app.conversation.domain.models

enum class IntentType {
    QUESTION,
    COMMAND,
    CODING_REQUEST,
    SEARCH_REQUEST,
    DEVICE_CONTROL_REQUEST,
    CASUAL_CONVERSATION,
    UNKNOWN_INTENT
}

data class ConversationMessage(
    val id: String,
    val role: Role,
    val text: String,
    val timestamp: Long,
    val intent: IntentType? = null
) {
    enum class Role {
        USER,
        ASSISTANT,
        SYSTEM
    }
}

data class ConversationContext(
    val currentTopic: String,
    val previousTopic: String,
    val sessionDurationMs: Long,
    val messageCount: Int
)
