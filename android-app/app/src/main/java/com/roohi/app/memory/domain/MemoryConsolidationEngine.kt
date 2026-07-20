package com.roohi.app.memory.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.memory.domain.models.MemoryRecord
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class MemoryConsolidationEngine @Inject constructor(
    private val repository: MemoryRepository,
    private val logger: Logger
) {
    private val mutex = Mutex()

    suspend fun consolidateMemories() {
        mutex.withLock {
            val memories = repository.getAllActiveMemories()
            val contentMap = mutableMapOf<String, MemoryRecord>()

            for (memory in memories) {
                val normalizedContent = memory.content.lowercase().trim()
                if (contentMap.containsKey(normalizedContent)) {
                    val existing = contentMap[normalizedContent]!!
                    if (memory.createdAt > existing.createdAt) {
                        repository.deleteMemory(existing.id)
                        contentMap[normalizedContent] = memory
                        logger.i("MemoryConsolidationEngine", "Consolidated duplicate memory: \${existing.id}")
                    } else {
                        repository.deleteMemory(memory.id)
                        logger.i("MemoryConsolidationEngine", "Consolidated duplicate memory: \${memory.id}")
                    }
                } else {
                    contentMap[normalizedContent] = memory
                }
            }
        }
    }
}
