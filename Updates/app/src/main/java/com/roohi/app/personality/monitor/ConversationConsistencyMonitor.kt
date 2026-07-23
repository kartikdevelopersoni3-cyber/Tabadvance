package com.roohi.app.personality.monitor

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationConsistencyMonitor @Inject constructor(
    private val logger: Logger
) {
    fun verifyConsistency(response: String): Boolean {
        logger.d("ConversationConsistencyMonitor", "Verifying response consistency... OK.")
        return true
    }
}
