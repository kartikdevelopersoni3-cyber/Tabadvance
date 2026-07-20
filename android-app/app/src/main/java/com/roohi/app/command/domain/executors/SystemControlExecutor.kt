package com.roohi.app.command.domain.executors

import android.content.Context
import android.media.AudioManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemControlExecutor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute(action: String): Pair<Boolean, String?> {
        return try {
            when (action) {
                "volume_up", "volume_down", "volume_mute" -> handleVolume(action)
                "flashlight_on", "flashlight_off" -> Pair(false, "Flashlight control requires camera permission/implementation.")
                "wifi_on", "wifi_off" -> Pair(false, "Settings navigation required for WiFi due to Android restrictions.")
                else -> Pair(false, "Unknown system action.")
            }
        } catch (e: SecurityException) {
            Pair(false, "Security exception: Missing permissions.")
        } catch (e: Exception) {
            Pair(false, "System error: ${e.message}")
        }
    }
    
    private fun handleVolume(action: String): Pair<Boolean, String?> {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            if (audioManager == null) return Pair(false, "AudioManager unavailable")
            
            when (action) {
                "volume_up" -> audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
                "volume_down" -> audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
                "volume_mute" -> audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI)
            }
            return Pair(true, null)
        } catch (e: SecurityException) {
            return Pair(false, "Not allowed to change volume policy.")
        } catch (e: Exception) {
            return Pair(false, "Failed to adjust volume: ${e.message}")
        }
    }
}
