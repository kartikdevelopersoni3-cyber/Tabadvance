package com.roohi.app.memory.domain.models

enum class MemoryType {
    SHORT_TERM,
    LONG_TERM,
    PREFERENCE,
    COMMAND,
    CONVERSATION,
    SYSTEM,
    TEMPORARY
}

data class MemoryRecord(
    val id: String,
    val memoryType: MemoryType,
    val content: String,
    val importanceScore: Float,
    val createdAt: Long,
    val updatedAt: Long,
    val lastAccessedAt: Long,
    val accessCount: Int,
    val sourceModule: String,
    val sessionId: String,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val expirationTime: Long?
)

data class MemoryDiagnostics(
    val totalCount: Int,
    val shortTermCount: Int,
    val longTermCount: Int
)
