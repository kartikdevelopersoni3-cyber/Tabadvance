package com.roohi.app.speech.domain

import com.roohi.app.speech.domain.models.SpeechResult
import kotlinx.coroutines.flow.Flow

interface SpeechRecognitionProvider {
    fun startListening(): Flow<SpeechResult>
    fun stopListening()
    fun destroy()
}
