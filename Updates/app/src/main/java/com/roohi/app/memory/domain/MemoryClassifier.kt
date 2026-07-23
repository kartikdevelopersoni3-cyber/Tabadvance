package com.roohi.app.memory.domain

import com.roohi.app.memory.domain.models.MemoryType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryClassifier @Inject constructor() {
    fun classifyContent(content: String): MemoryType {
        val lower = content.lowercase()
        return when {
            lower.contains("my favorite") || lower.contains("i like") -> MemoryType.PREFERENCE
            lower.contains("remind me") || lower.contains("remember to") -> MemoryType.COMMAND
            lower.contains("my name is") || lower.contains("i am") -> MemoryType.LONG_TERM
            lower.length < 50 -> MemoryType.SHORT_TERM
            else -> MemoryType.CONVERSATION
        }
    }
}
