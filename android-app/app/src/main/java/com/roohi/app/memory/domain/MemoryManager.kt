package com.roohi.app.memory.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.memory.domain.models.MemoryDiagnostics
import com.roohi.app.memory.domain.models.MemoryRecord
import com.roohi.app.memory.domain.models.MemoryType
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Singleton
class MemoryManager @Inject constructor(
    private val repository: MemoryRepository,
    private val classifier: MemoryClassifier,
    private val retrievalEngine: MemoryRetrievalEngine,
    private val consolidationEngine: MemoryConsolidationEngine,
    private val forgetEngine: MemoryForgetEngine,
    private val logger: Logger
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _memoryDiagnostics = MutableStateFlow(MemoryDiagnostics(0, 0, 0))
    val memoryDiagnostics: StateFlow<MemoryDiagnostics> = _memoryDiagnostics.asStateFlow()

    init {
        scope.launch {
            repository.observeActiveMemoryCount().collect { count ->
                val allMemories = repository.getAllActiveMemories()
                val shortCount = allMemories.count { it.memoryType == MemoryType.SHORT_TERM }
                val longCount = allMemories.count { it.memoryType == MemoryType.LONG_TERM }
                _memoryDiagnostics.value = MemoryDiagnostics(count, shortCount, longCount)
            }
        }
    }

    suspend fun addMemory(content: String, sourceModule: String, sessionId: String) {
        val type = classifier.classifyContent(content)
        val now = System.currentTimeMillis()
        val record = MemoryRecord(
            id = UUID.randomUUID().toString(),
            memoryType = type,
            content = content,
            importanceScore = if (type == MemoryType.PREFERENCE) 1.0f else 0.5f,
            createdAt = now,
            updatedAt = now,
            lastAccessedAt = now,
            accessCount = 0,
            sourceModule = sourceModule,
            sessionId = sessionId,
            isPinned = false,
            isArchived = false,
            expirationTime = if (type == MemoryType.SHORT_TERM) now + 3600000L else null // 1 hr
        )
        repository.saveMemory(record)
        logger.i("MemoryManager", "Memory added: \$content (Type: \$type)")
        
        scope.launch {
            consolidationEngine.consolidateMemories()
            forgetEngine.processDecay()
        }
    }

    suspend fun retrieveRelevantContext(query: String): String {
        val memories = retrievalEngine.searchRelevantMemories(query)
        if (memories.isEmpty()) return ""
        
        // update access stats
        for (m in memories.take(3)) {
            val updated = m.copy(lastAccessedAt = System.currentTimeMillis(), accessCount = m.accessCount + 1)
            repository.updateMemory(updated)
        }

        return memories.take(3).joinToString("; ") { it.content }
    }
}
