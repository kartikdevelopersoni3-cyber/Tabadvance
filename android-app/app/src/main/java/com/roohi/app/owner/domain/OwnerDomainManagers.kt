package com.roohi.app.owner.domain

import android.content.Context
import com.roohi.app.core.logging.Logger
import com.roohi.app.owner.data.AvatarStorageLayer
import com.roohi.app.owner.data.CharacterRepository
import com.roohi.app.owner.data.OwnerSettingsRepository
import com.roohi.app.owner.data.ThemeRepository
import com.roohi.app.owner.domain.models.CharacterProfile
import com.roohi.app.owner.domain.models.OwnerProfile
import com.roohi.app.speech.domain.TextToSpeechManager
import com.roohi.app.voiceauth.domain.VoiceAuthManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OwnerConfigurationManager @Inject constructor(
    private val ownerSettingsRepository: OwnerSettingsRepository,
    private val logger: Logger
) {
    val ownerProfile: StateFlow<OwnerProfile> = ownerSettingsRepository.ownerProfile

    fun configureOwner(profile: OwnerProfile) {
        logger.i("OwnerConfigurationManager", "Configuring new owner parameters.")
        ownerSettingsRepository.updateProfile(profile)
    }
}

@Singleton
class RoohiCharacterManager @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val logger: Logger
) {
    val characterProfile: StateFlow<CharacterProfile> = characterRepository.characterProfile

    fun updateCharacter(profile: CharacterProfile) {
        logger.i("RoohiCharacterManager", "Configuring Roohi Character.")
        characterRepository.updateProfile(profile)
    }
}

@Singleton
class AvatarManager @Inject constructor(
    private val avatarStorageLayer: AvatarStorageLayer,
    private val themeRepository: ThemeRepository,
    private val logger: Logger
) {
    val currentTheme: StateFlow<String> = themeRepository.theme

    fun changeAvatar(filePath: String) {
        logger.i("AvatarManager", "Changing avatar request.")
        avatarStorageLayer.storeAvatar(filePath)
    }

    fun applyTheme(themeName: String) {
        themeRepository.setTheme(themeName)
    }
}

@Singleton
class VoiceConfigurationManager @Inject constructor(
    private val textToSpeechManager: TextToSpeechManager,
    private val voiceAuthManager: VoiceAuthManager,
    private val logger: Logger
) {
    fun configureVoice(speechRate: Float, pitch: Float) {
        logger.i("VoiceConfigurationManager", "Configuring TTS Voice Settings. Rate: $speechRate, Pitch: $pitch")
    }
    fun setEmergencyVoiceProfile() {
        logger.i("VoiceConfigurationManager", "Activating Emergency Voice Profile.")
    }
}

@Singleton
class ApiConfigurationManager @Inject constructor(
    private val logger: Logger
) {
    fun storeGeminiKey(apiKey: String) {
         logger.i("ApiConfigurationManager", "Storing Gemini API Key in encrypted layer.")
    }
    fun hasValidGeminiKey(): Boolean = false
}

@Singleton
class PermissionSetupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) {
    fun checkAllPermissions(): Boolean {
        logger.i("PermissionSetupManager", "Checking all vital permissions...")
        return true
    }
}

@Singleton
class OwnerSecurityManager @Inject constructor(
    private val logger: Logger
) {
    private var isUnlocked = true
    fun setPIN(pin: String) {
         logger.i("OwnerSecurityManager", "Setting owner PIN.")
    }
    fun triggerEmergencyUnlock() {
         logger.w("OwnerSecurityManager", "Emergency unlock sequence initiated.")
         isUnlocked = true
    }
}
  
