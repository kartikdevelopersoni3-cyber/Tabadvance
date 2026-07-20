package com.roohi.app.identity.data.repository

import android.content.SharedPreferences
import com.roohi.app.identity.data.local.dao.IdentityDao
import com.roohi.app.identity.data.local.entity.DeviceMetadataEntity
import com.roohi.app.identity.data.local.entity.EmergencyContactEntity
import com.roohi.app.identity.data.local.entity.OwnerProfileEntity
import com.roohi.app.identity.domain.SystemIdentityRepository
import com.roohi.app.identity.domain.models.DeviceMetadata
import com.roohi.app.identity.domain.models.EmergencyContact
import com.roohi.app.identity.domain.models.OwnerProfile
import com.roohi.app.identity.domain.models.SetupState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemIdentityRepositoryImpl @Inject constructor(
    private val dao: IdentityDao,
    private val prefs: SharedPreferences // Standard prefs for non-secure config like setup_state
) : SystemIdentityRepository {

    override suspend fun saveOwnerProfile(profile: OwnerProfile) {
        dao.insertOwnerProfile(profile.toEntity())
    }

    override suspend fun getOwnerProfile(): OwnerProfile? {
        return dao.getOwnerProfile()?.toDomain()
    }

    override fun observeOwnerProfile(): Flow<OwnerProfile?> {
        return dao.observeOwnerProfile().map { it?.toDomain() }
    }

    override suspend fun clearOwnerProfile() {
        dao.clearOwnerProfile()
    }

    override suspend fun saveEmergencyContact(contact: EmergencyContact) {
        dao.insertEmergencyContact(contact.toEntity())
    }

    override suspend fun deleteEmergencyContact(id: String) {
        dao.deleteEmergencyContact(id)
    }

    override suspend fun getAllEmergencyContacts(): List<EmergencyContact> {
        return dao.getAllEmergencyContacts().map { it.toDomain() }
    }

    override fun observeEmergencyContacts(): Flow<List<EmergencyContact>> {
        return dao.observeEmergencyContacts().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun saveDeviceMetadata(metadata: DeviceMetadata) {
        dao.insertDeviceMetadata(metadata.toEntity())
    }

    override suspend fun getDeviceMetadata(): DeviceMetadata? {
        return dao.getDeviceMetadata()?.toDomain()
    }

    override suspend fun updateSetupState(state: SetupState) {
        prefs.edit().putString("setup_state_key", state.name).apply()
    }

    override suspend fun getSetupState(): SetupState {
        val stateStr = prefs.getString("setup_state_key", SetupState.NOT_STARTED.name) ?: SetupState.NOT_STARTED.name
        return try {
            SetupState.valueOf(stateStr)
        } catch (e: Exception) {
            SetupState.NOT_STARTED
        }
    }
}

// Mappers
fun OwnerProfileEntity.toDomain() = OwnerProfile(id, ownerName, nickname, language, region, emergencyPreferences, accessibilityPreferences, tabletPreferences, updatedAt)
fun OwnerProfile.toEntity() = OwnerProfileEntity(id, ownerName, nickname, language, region, emergencyPreferences, accessibilityPreferences, tabletPreferences, updatedAt)

fun EmergencyContactEntity.toDomain() = EmergencyContact(id, name, phoneNumber, relationship, isPrimary, isMedical, metadata)
fun EmergencyContact.toEntity() = EmergencyContactEntity(id, name, phoneNumber, relationship, isPrimary, isMedical, metadata)

fun DeviceMetadataEntity.toDomain() = DeviceMetadata(id, installationId, deviceId, tabletMetadata, androidVersion, buildVersion, registrationTimestamp, lastValidationTimestamp)
fun DeviceMetadata.toEntity() = DeviceMetadataEntity(id, installationId, deviceId, tabletMetadata, androidVersion, buildVersion, registrationTimestamp, lastValidationTimestamp)
