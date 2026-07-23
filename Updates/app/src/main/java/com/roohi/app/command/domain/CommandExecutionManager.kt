package com.roohi.app.command.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.command.domain.models.CommandType
import com.roohi.app.command.domain.models.ExecutionResult
import com.roohi.app.command.domain.executors.AppLaunchExecutor
import com.roohi.app.command.domain.executors.SystemControlExecutor
import com.roohi.app.command.domain.executors.ReminderExecutor
import com.roohi.app.command.domain.executors.SearchExecutor
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandExecutionManager @Inject constructor(
    private val classifier: CommandClassifier,
    private val validator: CommandValidator,
    private val appLaunchExecutor: AppLaunchExecutor,
    private val systemControlExecutor: SystemControlExecutor,
    private val reminderExecutor: ReminderExecutor,
    private val searchExecutor: SearchExecutor,
    private val resultManager: ExecutionResultManager,
    private val logger: Logger
) {
    private val executionMutex = Mutex()
    private var lastCommandText = ""
    private var lastCommandTime = 0L

    suspend fun executeCommand(text: String): String? {
        val command = classifier.classify(text)
        val initialValidation = validator.validate(command)
        if (!initialValidation.isValid) return null
        
        return executionMutex.withLock {
            val now = System.currentTimeMillis()
            if (text == lastCommandText && (now - lastCommandTime) < 2000) {
                logger.w("CommandExecutionManager", "Duplicate command rejected: $text")
                return@withLock "I am already doing that."
            }
            lastCommandText = text
            lastCommandTime = now

            val startTime = System.currentTimeMillis()
            var success = false
            var reason: String? = null
            var retryCount = 0
            val maxRetries = 1

            while (retryCount <= maxRetries && !success) {
                try {
                    val resultPair = withTimeoutOrNull(5000L) {
                        when (command.type) {
                            CommandType.APP_LAUNCH -> appLaunchExecutor.execute(command.payload)
                            CommandType.SYSTEM_CONTROL -> systemControlExecutor.execute(command.payload)
                            CommandType.REMINDER -> reminderExecutor.execute(command.payload)
                            CommandType.SEARCH -> searchExecutor.execute(command.payload)
                            else -> Pair(false, "Executor not mapped.")
                        }
                    }
                    if (resultPair == null) {
                        reason = "Execution timed out."
                        success = false
                    } else {
                        success = resultPair.first
                        reason = resultPair.second
                    }
                } catch (e: Exception) {
                    reason = "System Failure: ${e.message}"
                    success = false
                }
                
                if (!success && reason?.contains("timed out") == true) {
                    break
                }
                
                if (!success) retryCount++
            }
            
            val durationMs = System.currentTimeMillis() - startTime
            val result = ExecutionResult(
                commandId = UUID.randomUUID().toString(),
                command = command,
                success = success,
                reason = reason,
                executionDurationMs = durationMs,
                timestamp = System.currentTimeMillis()
            )
            
            resultManager.recordResult(result)
            
            if (success) {
                "Okay, done."
            } else {
                "I'm sorry, I couldn't do that. ${reason ?: ""}"
            }
        }
    }
}
