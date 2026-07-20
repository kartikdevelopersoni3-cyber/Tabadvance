package com.roohi.app.dsp

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MFCCGenerator @Inject constructor(
    private val melSpectrogramGenerator: MelSpectrogramGenerator
) {

    /**
     * Generates Mel-frequency cepstral coefficients (MFCCs).
     */
    fun generateMFCC(audioData: FloatArray, sampleRate: Int, numCepstra: Int = 13): Array<FloatArray> {
        val numFilters = 40
        val melSpectro = melSpectrogramGenerator.generateMelSpectrogram(audioData, sampleRate, numFilters)
        val frames = melSpectro.size
        
        if (frames == 0) return Array(0) { FloatArray(0) }

        val mfccs = Array(frames) { FloatArray(numCepstra) { 0.0f } }

        // Apply Log and Discrete Cosine Transform (DCT-II)
        val factor = Math.PI / numFilters
        for (i in 0 until frames) {
            val logMel = FloatArray(numFilters)
            for (j in 0 until numFilters) {
                logMel[j] = Math.log(melSpectro[i][j].toDouble()).toFloat()
            }

            for (k in 0 until numCepstra) {
                var sum = 0.0
                for (j in 0 until numFilters) {
                    sum += logMel[j] * Math.cos(factor * (j + 0.5) * k)
                }
                mfccs[i][k] = sum.toFloat()
            }
        }
        return mfccs
    }
}
