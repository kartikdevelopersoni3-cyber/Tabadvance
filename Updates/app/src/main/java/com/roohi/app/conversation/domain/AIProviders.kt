package com.roohi.app.conversation.domain

interface AIProvider {
    suspend fun generateResponse(prompt: String, history: List<com.roohi.app.conversation.domain.models.ConversationMessage>): String
}

interface GeminiProvider : AIProvider
interface LocalLLMProvider : AIProvider
interface FutureAIProvider : AIProvider
