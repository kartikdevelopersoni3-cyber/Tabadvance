package com.roohi.app.memory.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "memory_records")
data class MemoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "memory_type") val memoryType: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "importance_score") val importanceScore: Float,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "last_accessed_at") val lastAccessedAt: Long,
    @ColumnInfo(name = "access_count") val accessCount: Int,
    @ColumnInfo(name = "source_module") val sourceModule: String,
    @ColumnInfo(name = "session_id") val sessionId: String,
    @ColumnInfo(name = "is_pinned") val isPinned: Boolean,
    @ColumnInfo(name = "is_archived") val isArchived: Boolean,
    @ColumnInfo(name = "expiration_time") val expirationTime: Long?
)
