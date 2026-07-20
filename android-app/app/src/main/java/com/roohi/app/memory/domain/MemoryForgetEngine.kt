package com.roohi.app.memory.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class MemoryForgetEngine @Inject constructor(
    private val repository: MemoryRepository,
    private val logger: Logger
) {
    private val mutex = Mutex()

    suspend fun processDecay() {
        mutex.withLock {
            val currentTime = System.currentTimeMillis()
            val expired = repository.getExpiredMemories(currentTime)
            for (memory in expired) {
                if (!memory.isPinned) {
                    val arch = memory.copy(isArchived = true, updatedAt = currentTime)
                    repository.updateMemory(arch)
                    logger.i("MemoryForgetEngine", "Archived expired memory: \${memory.id}")
                }
            }
        }
    }
}
