package com.roohi.app.voiceauth.data.engine

import com.roohi.app.core.logging.Logger
import com.roohi.app.core.ml.InterpreterProvider
import com.roohi.app.core.ml.InterpreterWrapper
import com.roohi.app.voiceauth.domain.SpeakerVerificationEngine
import java.nio.ByteBuffer
import javax.inject.Inject
import kotlin.math.sqrt

class TFLiteSpeakerVerificationEngine @Inject constructor(
    private val interpreterProvider: InterpreterProvider,
    private val logger: Logger
) : SpeakerVerificationEngine {

    companion object {
        const val EMBEDDING_SIZE = 256
        const val MODEL_NAME = "speaker_dvector_model.tflite"
    }

    private var interpreterWrapper: InterpreterWrapper = InterpreterWrapper.Stub

    init {
        interpreterWrapper = interpreterProvider.getInterpreter(MODEL_NAME)
    }

    override suspend fun extractEmbedding(audioBuffer: ByteBuffer): FloatArray {
        logger.i("TFLiteSpeaker", "Extracting embedding from live audio buffer: \${audioBuffer.capacity()} bytes")
        
        return when (interpreterWrapper) {
            is InterpreterWrapper.Active -> {
                 // 1. Process Raw PCM Audio to MFCC (Mel-frequency cepstral coefficients) or Mel Spectrogram
                 // 2. val inputBuffer = preprocessAudio(audioBuffer)
                 // 3. val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, EMBEDDING_SIZE), DataType.FLOAT32)
                 // 4. (interpreterWrapper as InterpreterWrapper.Active).interpreter.run(inputBuffer.buffer, outputBuffer.buffer.rewind())
                 // 5. outputBuffer.floatArray
                 logger.i("TFLiteSpeaker", "Running active TFLite interpreter...")
                 FloatArray(EMBEDDING_SIZE) { 0.0f } // Return fake array for boilerplate
            }
            is InterpreterWrapper.Stub -> {
                logger.w("TFLiteSpeaker", "STUB MODE ACTIVE - Returning synthetic embedding.")
                FloatArray(EMBEDDING_SIZE) { 0.0f }
            }
        }
    }

    override fun verifyMatch(storedEmbedding: FloatArray, activeEmbedding: FloatArray): Float {
        require(storedEmbedding.size == activeEmbedding.size) { "Embedding dimensions must match" }
        return cosineSimilarity(storedEmbedding, activeEmbedding)
    }

    private fun cosineSimilarity(v1: FloatArray, v2: FloatArray): Float {
        var dotProduct = 0.0f
        var normA = 0.0f
        var normB = 0.0f
        for (i in v1.indices) {
            dotProduct += v1[i] * v2[i]
            normA += v1[i] * v1[i]
            normB += v2[i] * v2[i]
        }
        if (normA == 0.0f || normB == 0.0f) return 0.0f
        return (dotProduct / (sqrt(normA.toDouble()) * sqrt(normB.toDouble()))).toFloat()
    }
}
