package com.roohi.app.command.domain

import com.roohi.app.command.domain.models.ExecutionResult
import kotlinx.coroutines.flow.Flow

interface CommandRepository {
    suspend fun saveExecutionResult(result: ExecutionResult)
    suspend fun getExecutionHistory(): List<ExecutionResult>
    fun observeHistory(): Flow<List<ExecutionResult>>
}
