package com.roohi.app.speech.domain

interface SpeechRecognitionRepository {
    suspend fun saveLatestTranscript(transcript: String, confidence: Float)
    suspend fun getSessionTranscript(): String
    suspend fun clearSession()
}
