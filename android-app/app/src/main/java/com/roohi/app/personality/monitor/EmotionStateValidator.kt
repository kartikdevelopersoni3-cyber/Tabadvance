package com.roohi.app.personality.monitor

import com.roohi.app.core.logging.Logger
import com.roohi.app.personality.domain.models.SimulatedEmotion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmotionStateValidator @Inject constructor(
    private val logger: Logger
) {
    fun validateTransition(current: SimulatedEmotion, next: SimulatedEmotion): Boolean {
        if (current == SimulatedEmotion.EMERGENCY && next != SimulatedEmotion.RECOVERY && next != SimulatedEmotion.EMERGENCY) {
            logger.w("EmotionStateValidator", "Invalid transition from EMERGENCY blocked. Forcing RECOVERY.")
            return false
        }
        return true
    }
}
