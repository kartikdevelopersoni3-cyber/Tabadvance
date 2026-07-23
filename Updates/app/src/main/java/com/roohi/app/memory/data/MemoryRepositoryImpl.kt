package com.roohi.app.memory.data

import com.roohi.app.memory.domain.MemoryRepository
import com.roohi.app.memory.domain.models.MemoryRecord
import com.roohi.app.memory.domain.models.MemoryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryRepositoryImpl @Inject constructor(
    private val dao: MemoryDao
) : MemoryRepository {
    override suspend fun saveMemory(memory: MemoryRecord) {
        dao.insertMemory(memory.toEntity())
    }

    override suspend fun updateMemory(memory: MemoryRecord) {
        dao.updateMemory(memory.toEntity())
    }

    override suspend fun deleteMemory(id: String) {
        dao.deleteMemory(id)
    }

    override suspend fun getMemoryById(id: String): MemoryRecord? {
        return dao.getMemoryById(id)?.toDomain()
    }

    override suspend fun getAllActiveMemories(): List<MemoryRecord> {
        return dao.getAllActiveMemories().map { it.toDomain() }
    }

    override fun observeActiveMemories(): Flow<List<MemoryRecord>> {
        return dao.observeActiveMemories().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getExpiredMemories(currentTime: Long): List<MemoryRecord> {
        return dao.getExpiredMemories(currentTime).map { it.toDomain() }
    }

    override suspend fun getMemoriesByType(type: MemoryType): List<MemoryRecord> {
        return dao.getMemoriesByType(type.name).map { it.toDomain() }
    }

    override fun observeActiveMemoryCount(): Flow<Int> {
        return dao.observeActiveMemoryCount()
    }
}

fun MemoryEntity.toDomain() = MemoryRecord(
    id = id,
    memoryType = MemoryType.valueOf(memoryType),
    content = content,
    importanceScore = importanceScore,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastAccessedAt = lastAccessedAt,
    accessCount = accessCount,
    sourceModule = sourceModule,
    sessionId = sessionId,
    isPinned = isPinned,
    isArchived = isArchived,
    expirationTime = expirationTime
)

fun MemoryRecord.toEntity() = MemoryEntity(
    id = id,
    memoryType = memoryType.name,
    content = content,
    importanceScore = importanceScore,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastAccessedAt = lastAccessedAt,
    accessCount = accessCount,
    sourceModule = sourceModule,
    sessionId = sessionId,
    isPinned = isPinned,
    isArchived = isArchived,
    expirationTime = expirationTime
)
