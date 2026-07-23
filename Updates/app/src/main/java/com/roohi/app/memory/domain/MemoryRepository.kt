package com.roohi.app.memory.domain

import com.roohi.app.memory.domain.models.MemoryRecord
import com.roohi.app.memory.domain.models.MemoryType
import kotlinx.coroutines.flow.Flow

interface MemoryRepository {
    suspend fun saveMemory(memory: MemoryRecord)
    suspend fun updateMemory(memory: MemoryRecord)
    suspend fun deleteMemory(id: String)
    suspend fun getMemoryById(id: String): MemoryRecord?
    suspend fun getAllActiveMemories(): List<MemoryRecord>
    fun observeActiveMemories(): Flow<List<MemoryRecord>>
    suspend fun getExpiredMemories(currentTime: Long): List<MemoryRecord>
    suspend fun getMemoriesByType(type: MemoryType): List<MemoryRecord>
    fun observeActiveMemoryCount(): Flow<Int>
}
