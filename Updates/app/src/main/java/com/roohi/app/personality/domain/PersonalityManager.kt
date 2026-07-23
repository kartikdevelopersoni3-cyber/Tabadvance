package com.roohi.app.personality.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.personality.domain.models.PersonalityProfile
import com.roohi.app.personality.domain.models.PersonalityContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class PersonalityManager @Inject constructor(
    private val emotionSimulationEngine: EmotionSimulationEngine,
    private val ownerRelationshipEngine: OwnerRelationshipEngine,
    private val conversationStyleEngine: ConversationStyleEngine,
    private val personalityMemoryBridge: PersonalityMemoryBridge,
    private val tabletPresenceManager: TabletPresenceManager,
    private val logger: Logger
) {
    private val _profile = MutableStateFlow(PersonalityProfile())
    val profile: StateFlow<PersonalityProfile> = _profile.asStateFlow()

    suspend fun processResponse(rawResponse: String, intent: String, memoryContext: String): String {
        logger.i("PersonalityManager", "Processing response through personality engine.")
        
        val emotion = emotionSimulationEngine.getEmotionContext()
        val style = ownerRelationshipEngine.adaptCommunicationStyle(intent)
        
        val bridgedContext = personalityMemoryBridge.injectContext(memoryContext, "$emotion, Style: $style")
        return conversationStyleEngine.applyStyle("$rawResponse (Personality Check: $bridgedContext)", _profile.value, emotion)
    }

    fun updateEmotion(intent: String, isEmergency: Boolean = false) {
        if (isEmergency) {
            emotionSimulationEngine.transitionTo(
                com.roohi.app.personality.domain.models.SimulatedEmotion.EMERGENCY,
                "Emergency detected"
            )
        } else if (intent.contains("greet", true)) {
             emotionSimulationEngine.transitionTo(
                com.roohi.app.personality.domain.models.SimulatedEmotion.HAPPY,
                "Greeting intent"
            )
        } else {
             emotionSimulationEngine.transitionTo(
                com.roohi.app.personality.domain.models.SimulatedEmotion.NEUTRAL,
                "Default state"
            )
        }
    }
}
