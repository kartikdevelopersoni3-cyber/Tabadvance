package com.roohi.app.memory.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)

    @Update
    suspend fun updateMemory(memory: MemoryEntity)

    @Query("DELETE FROM memory_records WHERE id = :id")
    suspend fun deleteMemory(id: String)

    @Query("SELECT * FROM memory_records WHERE id = :id LIMIT 1")
    suspend fun getMemoryById(id: String): MemoryEntity?

    @Query("SELECT * FROM memory_records WHERE is_archived = 0 ORDER BY importance_score DESC, last_accessed_at DESC")
    suspend fun getAllActiveMemories(): List<MemoryEntity>

    @Query("SELECT * FROM memory_records WHERE is_archived = 0 ORDER BY importance_score DESC, last_accessed_at DESC")
    fun observeActiveMemories(): Flow<List<MemoryEntity>>

    @Query("SELECT * FROM memory_records WHERE expiration_time IS NOT NULL AND expiration_time < :currentTime")
    suspend fun getExpiredMemories(currentTime: Long): List<MemoryEntity>
    
    @Query("SELECT * FROM memory_records WHERE is_archived = 0 AND memory_type = :type")
    suspend fun getMemoriesByType(type: String): List<MemoryEntity>

    @Query("SELECT COUNT(*) FROM memory_records WHERE is_archived = 0")
    fun observeActiveMemoryCount(): Flow<Int>
}
