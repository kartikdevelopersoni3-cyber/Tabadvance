package com.roohi.app.personality.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonalityMemoryBridge @Inject constructor(
    private val logger: Logger
) {
    fun injectContext(memoryContext: String, personalityContext: String): String {
        logger.i("PersonalityMemoryBridge", "Injecting memory and personality context.")
        return "[$personalityContext] $memoryContext"
    }
}
