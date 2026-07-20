package com.roohi.app.voiceauth.domain

import com.roohi.app.voiceauth.domain.model.Voiceprint

interface VoiceProfileRepository {
    suspend fun getPrimaryVoiceprint(): Voiceprint?
    suspend fun saveVoiceprint(voiceprint: Voiceprint)
    suspend fun hasEnrolledVoiceprint(): Boolean
    suspend fun deleteVoiceprint(id: String)
}
