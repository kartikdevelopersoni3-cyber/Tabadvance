package com.roohi.app.owner.domain.models

data class OwnerProfile(
    val ownerName: String = "User",
    val nickname: String = "",
    val preferredLanguage: String = "en",
    val secondaryLanguage: String = "",
    val region: String = "US",
    val timeZone: String = "UTC",
    val preferredCommunicationStyle: String = "Direct",
    val ownerRole: String = "Custom",
    val accessibilityPreferences: String = "None",
    val tabletPreferences: String = "Standard",
    val emergencyPreferences: String = "Standard"
)

data class CharacterProfile(
    val characterName: String = "Roohi",
    val defaultPersonality: String = "Friendly",
    val humorLevel: Int = 5,
    val friendlinessLevel: Int = 8,
    val professionalismLevel: Int = 8,
    val talkativenessLevel: Int = 5,
    val teachingStyle: String = "Socratic",
    val motivationStyle: String = "Encouraging",
    val responseLengthPreference: String = "Medium",
    val emergencyBehaviorMode: String = "Strict"
)
  
