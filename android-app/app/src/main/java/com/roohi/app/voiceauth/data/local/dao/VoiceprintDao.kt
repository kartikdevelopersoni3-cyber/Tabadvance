package com.roohi.app.voiceauth.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roohi.app.voiceauth.data.local.entity.VoiceprintEntity

@Dao
interface VoiceprintDao {
    @Query("SELECT * FROM owner_voiceprints LIMIT 1") // Assuming single owner for now, can support multiple
    suspend fun getPrimaryOwnerVoiceprint(): VoiceprintEntity?

    @Query("SELECT * FROM owner_voiceprints")
    suspend fun getAllVoiceprints(): List<VoiceprintEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVoiceprint(voiceprint: VoiceprintEntity)

    @Query("DELETE FROM owner_voiceprints WHERE id = :id")
    suspend fun deleteVoiceprint(id: String)

    @Query("DELETE FROM owner_voiceprints")
    suspend fun clearAll()
}
