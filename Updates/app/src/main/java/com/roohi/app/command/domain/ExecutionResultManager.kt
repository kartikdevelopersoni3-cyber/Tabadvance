package com.roohi.app.command.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.command.domain.models.ExecutionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExecutionResultManager @Inject constructor(
    private val commandRepository: CommandRepository,
    private val logger: Logger
) {
    private val _successCount = MutableStateFlow(0)
    val successCount: StateFlow<Int> = _successCount.asStateFlow()

    private val _failureCount = MutableStateFlow(0)
    val failureCount: StateFlow<Int> = _failureCount.asStateFlow()

    private val _lastResult = MutableStateFlow<ExecutionResult?>(null)
    val lastResult: StateFlow<ExecutionResult?> = _lastResult.asStateFlow()

    suspend fun recordResult(result: ExecutionResult) {
        commandRepository.saveExecutionResult(result)
        _lastResult.value = result
        if (result.success) {
            _successCount.value += 1
            logger.i("ExecutionResultManager", "Command success: ${result.command.type} (${result.executionDurationMs}ms)")
        } else {
            _failureCount.value += 1
            logger.e("ExecutionResultManager", "Command failure: ${result.command.type} -> ${result.reason}")
        }
    }
}
