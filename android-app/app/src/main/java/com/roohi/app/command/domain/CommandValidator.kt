package com.roohi.app.command.domain

import com.roohi.app.command.domain.models.CommandType
import com.roohi.app.command.domain.models.ParsedCommand
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandValidator @Inject constructor() {
    fun validate(command: ParsedCommand): ValidationResult {
        if (command.type == CommandType.UNSUPPORTED) {
            return ValidationResult(false, "Unsupported command format.")
        }
        if (command.payload.isBlank() && command.type != CommandType.SYSTEM_CONTROL) {
            return ValidationResult(false, "Empty payload for command.")
        }
        
        // Context/Permission constraints check could go here
        return ValidationResult(true, null)
    }
}

data class ValidationResult(val isValid: Boolean, val reason: String?)
