package com.roohi.app.wakeword.domain

import java.nio.ByteBuffer
import kotlinx.coroutines.flow.Flow

/**
 * Clean architecture interface for Wake Word Detection.
 * Allows swapping Porcupine, Snowboy, or TFLite custom models without changing business logic.
 */
interface WakeWordEngine {
    
    /**
     * Initializes the underlying audio models and word configurations (e.g. "Roohi").
     */
    suspend fun initialize()

    /**
     * Starts continuous listening on the provided raw PCM audio flow.
     * Emits a trigger when the wake word is detected.
     * @return Flow of a ByteBuffer representing the buffered audio _immediately_ following
     * the wake word, which is passed to VoiceAuthManager.
     */
    fun startListening(audioStream: Flow<ShortArray>): Flow<ByteBuffer>

    /**
     * Stops the engine and releases microphone usage.
     */
    fun stopListening()
}
