package com.roohi.app.dsp

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureExtractionManager @Inject constructor(
    private val audioPreprocessor: AudioPreprocessor,
    private val melSpectrogramGenerator: MelSpectrogramGenerator,
    private val mfccGenerator: MFCCGenerator,
    private val logger: Logger
) {

    fun extractRawPcm(pcm: ShortArray): FloatArray {
        return audioPreprocessor.normalize(pcm)
    }

    fun extractMelSpectrogram(pcm: ShortArray, sampleRate: Int): Array<FloatArray> {
        val normalized = audioPreprocessor.normalize(pcm)
        val trimmed = audioPreprocessor.removeSilence(normalized)
        return melSpectrogramGenerator.generateMelSpectrogram(trimmed, sampleRate)
    }

    fun extractMFCC(pcm: ShortArray, sampleRate: Int): Array<FloatArray> {
        val normalized = audioPreprocessor.normalize(pcm)
        val trimmed = audioPreprocessor.removeSilence(normalized)
        return mfccGenerator.generateMFCC(trimmed, sampleRate)
    }
}
