package com.roohi.app.personality.monitor

import com.roohi.app.core.logging.Logger
import com.roohi.app.personality.domain.EmotionSimulationEngine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonalityRecoveryEngine @Inject constructor(
    private val emotionSimulationEngine: EmotionSimulationEngine,
    private val logger: Logger
) {
    fun recover() {
        logger.w("PersonalityRecoveryEngine", "Resetting emotional state to Neutral.")
        emotionSimulationEngine.reset()
    }
}
