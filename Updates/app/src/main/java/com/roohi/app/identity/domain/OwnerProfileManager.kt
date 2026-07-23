package com.roohi.app.identity.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.models.OwnerProfile
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class OwnerProfileManager @Inject constructor(
    private val repository: SystemIdentityRepository,
    private val logger: Logger
) {
    suspend fun createOrUpdateProfile(name: String, nickname: String, language: String, region: String) {
        val existing = repository.getOwnerProfile()
        val profile = existing?.copy(
            ownerName = name,
            nickname = nickname,
            language = language,
            region = region,
            updatedAt = System.currentTimeMillis()
        ) ?: OwnerProfile(
            id = UUID.randomUUID().toString(),
            ownerName = name,
            nickname = nickname,
            language = language,
            region = region,
            emergencyPreferences = "default",
            accessibilityPreferences = "default",
            tabletPreferences = "default",
            updatedAt = System.currentTimeMillis()
        )
        repository.saveOwnerProfile(profile)
        logger.i("OwnerProfileManager", "Owner profile updated successfully.")
    }

    suspend fun getProfile(): OwnerProfile? {
        return repository.getOwnerProfile()
    }
}
