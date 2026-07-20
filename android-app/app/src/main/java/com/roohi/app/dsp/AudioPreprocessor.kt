package com.roohi.app.dsp

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs
import kotlin.math.max

@Singleton
class AudioPreprocessor @Inject constructor(
    private val logger: Logger
) {

    // Rolling state for Automatic Gain Control (AGC) style normalization
    private var rollingMaxAmplitude = 0.001f
    private const val DECAY_FACTOR = 0.999f

    /**
     * Normalizes the PCM array to a standard amplitude range [-1.0, 1.0]
     * using a rolling window to preserve SNR and handle continuous streams.
     */
    fun normalize(pcmData: ShortArray): FloatArray {
        if (pcmData.isEmpty()) return FloatArray(0)
        
        val normalized = FloatArray(pcmData.size)
        for (i in pcmData.indices) {
            val amplitude = abs(pcmData[i].toFloat())
            if (amplitude > rollingMaxAmplitude) {
                rollingMaxAmplitude = amplitude
            } else {
                rollingMaxAmplitude *= DECAY_FACTOR
            }
            // Ensure minimum threshold to prevent divide by zero / extreme amplification of noise
            val scale = 1.0f / max(rollingMaxAmplitude, 100f)
            normalized[i] = pcmData[i] * scale
            // Hard clip just in case
            if (normalized[i] > 1.0f) normalized[i] = 1.0f
            if (normalized[i] < -1.0f) normalized[i] = -1.0f
        }
        
        return normalized
    }

    /**
     * Trims leading and trailing silence based on a given threshold.
     */
    fun removeSilence(normalizedData: FloatArray, threshold: Float = 0.01f): FloatArray {
        var startIdx = 0
        var endIdx = normalizedData.size - 1

        while (startIdx < normalizedData.size && abs(normalizedData[startIdx]) < threshold) {
            startIdx++
        }

        while (endIdx > startIdx && abs(normalizedData[endIdx]) < threshold) {
            endIdx--
        }

        if (startIdx > endIdx) {
            return FloatArray(0)
        }

        return normalizedData.sliceArray(startIdx..endIdx)
    }
}
