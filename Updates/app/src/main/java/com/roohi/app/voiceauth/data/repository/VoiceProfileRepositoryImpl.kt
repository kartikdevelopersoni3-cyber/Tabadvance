package com.roohi.app.voiceauth.data.repository

import com.roohi.app.core.security.EncryptionManager
import com.roohi.app.voiceauth.data.local.dao.VoiceprintDao
import com.roohi.app.voiceauth.data.local.entity.VoiceprintEntity
import com.roohi.app.voiceauth.domain.VoiceProfileRepository
import com.roohi.app.voiceauth.domain.model.Voiceprint
import javax.inject.Inject

class VoiceProfileRepositoryImpl @Inject constructor(
    private val voiceprintDao: VoiceprintDao,
    private val encryptionManager: EncryptionManager
) : VoiceProfileRepository {

    override suspend fun getPrimaryVoiceprint(): Voiceprint? {
        val entity = voiceprintDao.getPrimaryOwnerVoiceprint() ?: return null
        val decryptedData = encryptionManager.decrypt(entity.embeddingData)
        return Voiceprint(
            id = entity.id,
            ownerName = entity.ownerName,
            embedding = decryptedData.split(",").map { it.toFloat() }.toFloatArray(),
            createdAt = entity.enrolledTimestamp
        )
    }

    override suspend fun saveVoiceprint(voiceprint: Voiceprint) {
        val rawData = voiceprint.embedding.joinToString(",")
        val encryptedData = encryptionManager.encrypt(rawData)
        val entity = VoiceprintEntity(
            id = voiceprint.id,
            ownerName = voiceprint.ownerName,
            embeddingData = encryptedData,
            enrolledTimestamp = voiceprint.createdAt
        )
        voiceprintDao.insertVoiceprint(entity)
    }

    override suspend fun hasEnrolledVoiceprint(): Boolean {
        return voiceprintDao.getPrimaryOwnerVoiceprint() != null
    }

    override suspend fun deleteVoiceprint(id: String) {
        voiceprintDao.deleteVoiceprint(id)
    }
}
