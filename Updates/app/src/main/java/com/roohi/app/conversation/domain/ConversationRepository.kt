package com.roohi.app.conversation.domain

import com.roohi.app.conversation.domain.models.ConversationMessage
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun saveMessage(message: ConversationMessage)
    suspend fun getSessionHistory(): List<ConversationMessage>
    fun observeHistory(): Flow<List<ConversationMessage>>
    suspend fun clearSession()
}
