package com.roohi.app.owner.data

import com.roohi.app.core.logging.Logger
import com.roohi.app.owner.domain.models.CharacterProfile
import com.roohi.app.owner.domain.models.OwnerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OwnerSettingsRepository @Inject constructor(
    private val logger: Logger
) {
    private val _ownerProfile = MutableStateFlow(OwnerProfile())
    val ownerProfile: StateFlow<OwnerProfile> = _ownerProfile.asStateFlow()

    fun updateProfile(profile: OwnerProfile) {
        logger.i("OwnerSettingsRepository", "Updating Owner Profile")
        _ownerProfile.value = profile
    }
}

@Singleton
class CharacterRepository @Inject constructor(
    private val logger: Logger
) {
    private val _characterProfile = MutableStateFlow(CharacterProfile())
    val characterProfile: StateFlow<CharacterProfile> = _characterProfile.asStateFlow()

    fun updateProfile(profile: CharacterProfile) {
        logger.i("CharacterRepository", "Updating Character Profile")
        _characterProfile.value = profile
    }
}

@Singleton
class ThemeRepository @Inject constructor(
    private val logger: Logger
) {
    private val _theme = MutableStateFlow("Robotic UI Mode")
    val theme: StateFlow<String> = _theme.asStateFlow()

    fun setTheme(themeName: String) {
        logger.i("ThemeRepository", "Setting theme to $themeName")
        _theme.value = themeName
    }
}

@Singleton
class AvatarStorageLayer @Inject constructor(
    private val logger: Logger
) {
    fun storeAvatar(filePath: String): Boolean {
        logger.i("AvatarStorageLayer", "Storing avatar from $filePath")
        return true
    }
    fun getAvatarPath(): String {
        return "default_avatar.png"
    }
}
  
