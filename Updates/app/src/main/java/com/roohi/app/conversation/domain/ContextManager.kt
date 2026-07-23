package com.roohi.app.conversation.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.conversation.domain.models.ConversationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextManager @Inject constructor(
    private val logger: Logger
) {
    private var currentTopic: String = "general"
    private var previousTopic: String = "none"
    private var sessionStartTime: Long = System.currentTimeMillis()
    private var messageCount: Int = 0
    private val mutex = Mutex()

    suspend fun updateContext(inputText: String, intent: com.roohi.app.conversation.domain.models.IntentType) {
        mutex.withLock {
            val newTopic = extractTopic(inputText, intent)
            if (newTopic != currentTopic) {
                previousTopic = currentTopic
                currentTopic = newTopic
                logger.i("ContextManager", "Topic shifted from $previousTopic to $currentTopic")
            }
            messageCount++
        }
    }

    suspend fun getContext(): ConversationContext {
        return mutex.withLock {
            ConversationContext(
                currentTopic = currentTopic,
                previousTopic = previousTopic,
                sessionDurationMs = System.currentTimeMillis() - sessionStartTime,
                messageCount = messageCount
            )
        }
    }

    private fun extractTopic(text: String, intent: com.roohi.app.conversation.domain.models.IntentType): String {
        val lowerText = text.lowercase()
        return when {
            lowerText.contains("weather") -> "weather"
            lowerText.contains("time") -> "time"
            lowerText.contains("code") || lowerText.contains("debug") -> "coding"
            intent == com.roohi.app.conversation.domain.models.IntentType.DEVICE_CONTROL_REQUEST -> "device_control"
            else -> currentTopic
        }
    }

    suspend fun resetSession() {
        mutex.withLock {
            currentTopic = "general"
            previousTopic = "none"
            sessionStartTime = System.currentTimeMillis()
            messageCount = 0
            logger.i("ContextManager", "Session context reset.")
        }
    }
}
