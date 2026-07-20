package com.roohi.app.command.data

import com.roohi.app.command.domain.CommandRepository
import com.roohi.app.command.domain.models.ExecutionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandRepositoryImpl @Inject constructor() : CommandRepository {
    private val history = CopyOnWriteArrayList<ExecutionResult>()
    private val _historyFlow = MutableStateFlow<List<ExecutionResult>>(emptyList())

    override suspend fun saveExecutionResult(result: ExecutionResult) {
        history.add(result)
        _historyFlow.value = history.toList()
    }

    override suspend fun getExecutionHistory(): List<ExecutionResult> {
        return history.toList()
    }

    override fun observeHistory(): Flow<List<ExecutionResult>> {
        return _historyFlow.asStateFlow()
    }
}
