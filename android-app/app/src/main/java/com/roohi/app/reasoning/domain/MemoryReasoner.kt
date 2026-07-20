package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.memory.domain.MemoryManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryReasoner @Inject constructor(
    private val memoryManager: MemoryManager,
    private val logger: Logger
) {
    suspend fun analyzeMemory(intent: String): String {
        logger.d("MemoryReasoner", "Analyzing cross-referenced memory chunks for intent: $intent")
        val memories = memoryManager.retrieveRelevantContext(intent)
        return if (memories.isBlank()) "NO_RELEVANT_MEMORIES" else memories
    }
}
