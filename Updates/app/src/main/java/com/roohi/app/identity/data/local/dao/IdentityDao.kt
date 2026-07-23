package com.roohi.app.identity.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.roohi.app.identity.data.local.entity.OwnerProfileEntity
import com.roohi.app.identity.data.local.entity.EmergencyContactEntity
import com.roohi.app.identity.data.local.entity.DeviceMetadataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IdentityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnerProfile(profile: OwnerProfileEntity)

    @Update
    suspend fun updateOwnerProfile(profile: OwnerProfileEntity)

    @Query("SELECT * FROM owner_profile LIMIT 1")
    suspend fun getOwnerProfile(): OwnerProfileEntity?

    @Query("SELECT * FROM owner_profile LIMIT 1")
    fun observeOwnerProfile(): Flow<OwnerProfileEntity?>

    @Query("DELETE FROM owner_profile")
    suspend fun clearOwnerProfile()

    // Emergency Contacts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContact(contact: EmergencyContactEntity)
    
    @Update
    suspend fun updateEmergencyContact(contact: EmergencyContactEntity)
    
    @Query("DELETE FROM emergency_contacts WHERE id = :id")
    suspend fun deleteEmergencyContact(id: String)
    
    @Query("SELECT * FROM emergency_contacts")
    suspend fun getAllEmergencyContacts(): List<EmergencyContactEntity>
    
    @Query("SELECT * FROM emergency_contacts")
    fun observeEmergencyContacts(): Flow<List<EmergencyContactEntity>>
    
    // Device Metadata
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceMetadata(metadata: DeviceMetadataEntity)
    
    @Query("SELECT * FROM device_metadata LIMIT 1")
    suspend fun getDeviceMetadata(): DeviceMetadataEntity?
}
