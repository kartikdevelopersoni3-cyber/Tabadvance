package com.roohi.app.identity.domain

import com.roohi.app.identity.domain.models.DeviceMetadata
import com.roohi.app.identity.domain.models.EmergencyContact
import com.roohi.app.identity.domain.models.OwnerProfile
import com.roohi.app.identity.domain.models.SetupState
import kotlinx.coroutines.flow.Flow

interface SystemIdentityRepository {
    suspend fun saveOwnerProfile(profile: OwnerProfile)
    suspend fun getOwnerProfile(): OwnerProfile?
    fun observeOwnerProfile(): Flow<OwnerProfile?>
    suspend fun clearOwnerProfile()

    suspend fun saveEmergencyContact(contact: EmergencyContact)
    suspend fun deleteEmergencyContact(id: String)
    suspend fun getAllEmergencyContacts(): List<EmergencyContact>
    fun observeEmergencyContacts(): Flow<List<EmergencyContact>>

    suspend fun saveDeviceMetadata(metadata: DeviceMetadata)
    suspend fun getDeviceMetadata(): DeviceMetadata?
    
    suspend fun updateSetupState(state: SetupState)
    suspend fun getSetupState(): SetupState
}
