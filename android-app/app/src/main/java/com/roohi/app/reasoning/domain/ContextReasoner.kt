package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.OwnerProfileManager
import com.roohi.app.memory.domain.MemoryRetrievalEngine
import com.roohi.app.reasoning.domain.models.ReasoningContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextReasoner @Inject constructor(
    private val memoryRetrievalEngine: MemoryRetrievalEngine,
    private val ownerProfileManager: OwnerProfileManager,
    private val logger: Logger
) {
    suspend fun buildContext(query: String, intent: String): ReasoningContext {
        logger.i("ContextReasoner", "Building reasoning context for query: $query")
        
        val memories = memoryRetrievalEngine.retrieveRelevantMemories(query, limit = 5)
        val profile = ownerProfileManager.getProfile()
        val prefs = mapOf(
            "name" to (profile?.nickname ?: "Owner"),
            "language" to (profile?.language ?: "en"),
            "emergency_prefs" to (profile?.emergencyPreferences ?: "default")
        )
        
        return ReasoningContext(
            currentIntent = intent,
            relevantMemories = memories.map { it.content },
            ownerPreferences = prefs,
            deviceStatus = "ONLINE_TABLET"
        )
    }
}
