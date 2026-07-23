package com.roohi.app.personality.domain.models

data class PersonalityProfile(
    val name: String = "Roohi",
    val assistantIdentity: String = "Helpful Tablet Assistant",
    val speakingStyle: String = "Natural",
    val humorLevel: Int = 5,
    val professionalLevel: Int = 8,
    val friendlinessLevel: Int = 8,
    val formalityLevel: Int = 5,
    val conversationEnergy: Int = 7
)

enum class SimulatedEmotion {
    NEUTRAL, HAPPY, EXCITED, FOCUSED, CURIOUS, SUPPORTIVE, ALERT, EMERGENCY, PROFESSIONAL, RECOVERY
}

data class PersonalityContext(
    val currentEmotion: SimulatedEmotion,
    val ownerFamiliarityScore: Int,
    val currentStyle: String
)
