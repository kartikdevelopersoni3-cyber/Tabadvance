package com.roohi.app.conversation.data

import com.roohi.app.conversation.domain.ConversationRepository
import com.roohi.app.conversation.domain.models.ConversationMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepositoryImpl @Inject constructor() : ConversationRepository {

    private val sessionHistory = CopyOnWriteArrayList<ConversationMessage>()
    private val _historyFlow = MutableStateFlow<List<ConversationMessage>>(emptyList())

    override suspend fun saveMessage(message: ConversationMessage) {
        sessionHistory.add(message)
        _historyFlow.value = sessionHistory.toList()
    }

    override suspend fun getSessionHistory(): List<ConversationMessage> {
        return sessionHistory.toList()
    }

    override fun observeHistory(): Flow<List<ConversationMessage>> {
        return _historyFlow.asStateFlow()
    }

    override suspend fun clearSession() {
        sessionHistory.clear()
        _historyFlow.value = emptyList()
    }
}
