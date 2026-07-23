package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.owner.domain.OwnerConfigurationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceReasoner @Inject constructor(
    private val ownerConfigurationManager: OwnerConfigurationManager,
    private val logger: Logger
) {
    fun evaluatePreferences(): String {
        val profile = ownerConfigurationManager.ownerProfile.value
        logger.d("PreferenceReasoner", "Evaluating owner preference bounds.")
        return "Style: ${profile.preferredCommunicationStyle}, Role: ${profile.ownerRole}"
    }
}
