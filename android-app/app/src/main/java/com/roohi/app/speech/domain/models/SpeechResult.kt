package com.roohi.app.speech.domain.models

data class SpeechResult(
    val transcript: String,
    val isPartial: Boolean,
    val confidenceScore: Float = 0.0f,
    val error: String? = null
)
