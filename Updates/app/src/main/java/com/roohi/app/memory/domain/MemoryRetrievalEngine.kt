package com.roohi.app.memory.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.memory.domain.models.MemoryRecord
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class MemoryRetrievalEngine @Inject constructor(
    private val repository: MemoryRepository,
    private val logger: Logger
) {
    private val mutex = Mutex()

    suspend fun searchRelevantMemories(query: String): List<MemoryRecord> {
        return mutex.withLock {
            val allActives = repository.getAllActiveMemories()
            val queryWords = query.lowercase().split(Regex("\\s+")).filter { it.isNotBlank() }
            
            if (queryWords.isEmpty()) return@withLock emptyList()

            allActives.filter { memory ->
                val lowerContent = memory.content.lowercase()
                queryWords.any { word -> lowerContent.contains(word) }
            }.sortedByDescending { it.importanceScore }
        }
    }
}
