package com.roohi.app.command.domain

import com.roohi.app.command.domain.models.CommandType
import com.roohi.app.command.domain.models.ParsedCommand
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandClassifier @Inject constructor() {
    fun classify(text: String): ParsedCommand {
        val lowerText = text.lowercase()
        return when {
            lowerText.startsWith("open ") || lowerText.startsWith("launch ") -> {
                val app = lowerText.replace("open ", "").replace("launch ", "").trim()
                ParsedCommand(CommandType.APP_LAUNCH, app, text)
            }
            lowerText.contains("volume") || lowerText.contains("brightness") || lowerText.contains("flashlight") || lowerText.contains("turn wifi") || lowerText.contains("turn bluetooth") -> {
                val action = extractSystemAction(lowerText)
                ParsedCommand(CommandType.SYSTEM_CONTROL, action, text)
            }
            lowerText.contains("play music") || lowerText.contains("stop music") -> {
                val action = if (lowerText.contains("play")) "play" else "stop"
                ParsedCommand(CommandType.MEDIA_CONTROL, action, text)
            }
            lowerText.startsWith("remind me") || lowerText.contains("set reminder") -> {
                ParsedCommand(CommandType.REMINDER, lowerText, text)
            }
            lowerText.startsWith("search") || lowerText.startsWith("google") -> {
                val query = lowerText.replace("search for", "").replace("search", "").replace("google", "").trim()
                ParsedCommand(CommandType.SEARCH, query, text)
            }
            lowerText.contains("navigate") || lowerText.contains("directions to") -> {
                ParsedCommand(CommandType.NAVIGATION, lowerText, text)
            }
            else -> {
                ParsedCommand(CommandType.UNSUPPORTED, "", text)
            }
        }
    }

    private fun extractSystemAction(text: String): String {
        return when {
            text.contains("increase volume") || text.contains("volume up") -> "volume_up"
            text.contains("decrease volume") || text.contains("volume down") -> "volume_down"
            text.contains("mute") -> "volume_mute"
            text.contains("flashlight on") -> "flashlight_on"
            text.contains("flashlight off") -> "flashlight_off"
            text.contains("turn wifi on") -> "wifi_on"
            text.contains("turn wifi off") -> "wifi_off"
            text.contains("turn bluetooth on") -> "bluetooth_on"
            text.contains("turn bluetooth off") -> "bluetooth_off"
            else -> "unknown"
        }
    }
}
