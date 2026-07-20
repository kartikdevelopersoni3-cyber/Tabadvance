package com.roohi.app.command.domain.models

enum class CommandType {
    APP_LAUNCH,
    SYSTEM_CONTROL,
    MEDIA_CONTROL,
    REMINDER,
    SEARCH,
    NAVIGATION,
    INFORMATION,
    UNSUPPORTED
}

data class ParsedCommand(
    val type: CommandType,
    val payload: String, 
    val originalText: String
)

data class ExecutionResult(
    val commandId: String,
    val command: ParsedCommand,
    val success: Boolean,
    val reason: String?,
    val executionDurationMs: Long,
    val timestamp: Long
)
