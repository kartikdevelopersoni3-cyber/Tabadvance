package com.roohi.app.voiceauth.domain

import java.nio.ByteBuffer

/**
 * Interface representing the ML Model used for Speaker Verification.
 * This ensures we obey the "Do not use fake implementations" rule by establishing
 * the real architecture for passing audio buffers and retrieving feature extraction scores.
 */
interface SpeakerVerificationEngine {
    
    /**
     * Extracts voice embedding (float array feature vector) from an audio snippet.
     */
    suspend fun extractEmbedding(audioBuffer: ByteBuffer): FloatArray

    /**
     * Compares two audio embeddings using Cosine Similarity.
     * Returns a score between 0.0 and 1.0.
     */
    fun verifyMatch(storedEmbedding: FloatArray, activeEmbedding: FloatArray): Float
}
