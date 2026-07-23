package com.roohi.app.speech.data.repository

import com.roohi.app.speech.domain.SpeechRecognitionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechRecognitionRepositoryImpl @Inject constructor() : SpeechRecognitionRepository {
    
    private val sessionBuilder = StringBuilder()
    private var lastConfidence: Float = 0.0f

    override suspend fun saveLatestTranscript(transcript: String, confidence: Float) {
        if (sessionBuilder.isNotEmpty()) {
            sessionBuilder.append(" ")
        }
        sessionBuilder.append(transcript)
        lastConfidence = confidence
    }

    override suspend fun getSessionTranscript(): String {
        return sessionBuilder.toString()
    }

    override suspend fun clearSession() {
        sessionBuilder.clear()
        lastConfidence = 0.0f
    }
}
