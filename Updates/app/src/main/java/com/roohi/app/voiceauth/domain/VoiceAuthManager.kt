package com.roohi.app.voiceauth.domain

import com.roohi.app.core.logging.Logger
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceAuthManager @Inject constructor(
    private val repository: VoiceProfileRepository,
    private val engine: SpeakerVerificationEngine,
    private val logger: Logger
) {

    companion object {
        const val VERIFICATION_THRESHOLD = 0.75f // 75% confidence limit
    }

    suspend fun verifyActiveAudio(audioBuffer: ByteBuffer): Boolean {
        logger.i("VoiceAuthManager", "Starting verification of incoming audio payload.")

        val primaryPrint = repository.getPrimaryVoiceprint()
        if (primaryPrint == null) {
            logger.e("VoiceAuthManager", "No enrolled voiceprint found. Security block active.")
            return false
        }

        val activeEmbedding = engine.extractEmbedding(audioBuffer)
        val score = engine.verifyMatch(primaryPrint.embedding, activeEmbedding)
        
        logger.d("VoiceAuthManager", "Verification score: \$score vs Threshold: \$VERIFICATION_THRESHOLD")

        if (score >= VERIFICATION_THRESHOLD) {
            logger.i("VoiceAuthManager", "SUCCESS: Owner match authorized.")
            return true
        } else {
            logger.w("VoiceAuthManager", "FAILURE: Unknown speaker. Payload rejected.")
            return false
        }
    }
}
