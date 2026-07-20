package com.roohi.app.personality.monitor

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonalityHealthMonitor @Inject constructor(
    private val personalityValidator: PersonalityValidator,
    private val personalityRecoveryEngine: PersonalityRecoveryEngine,
    private val logger: Logger
) {
    fun checkHealth(): Boolean {
        if (!personalityValidator.validateConsistency()) {
            logger.e("PersonalityHealthMonitor", "Personality drift detected. Triggering recovery.")
            personalityRecoveryEngine.recover()
            return false
        } else {
            logger.d("PersonalityHealthMonitor", "Personality Engine healthy.")
            return true
        }
    }
}
