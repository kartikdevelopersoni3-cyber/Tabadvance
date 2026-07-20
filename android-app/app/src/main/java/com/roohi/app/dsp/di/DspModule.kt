package com.roohi.app.dsp.di

import com.roohi.app.core.logging.Logger
import com.roohi.app.dsp.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DspModule {

    @Provides
    @Singleton
    fun provideSampleRateManager(logger: Logger): SampleRateManager {
        return SampleRateManager(logger)
    }

    @Provides
    @Singleton
    fun provideAudioPreprocessor(logger: Logger): AudioPreprocessor {
        return AudioPreprocessor(logger)
    }

    @Provides
    @Singleton
    fun provideFFTProcessor(logger: Logger): FFTProcessor {
        return FFTProcessor(logger)
    }

    @Provides
    @Singleton
    fun provideMelSpectrogramGenerator(fftProcessor: FFTProcessor): MelSpectrogramGenerator {
        return MelSpectrogramGenerator(fftProcessor)
    }

    @Provides
    @Singleton
    fun provideMFCCGenerator(melSpectrogramGenerator: MelSpectrogramGenerator): MFCCGenerator {
        return MFCCGenerator(melSpectrogramGenerator)
    }

    @Provides
    @Singleton
    fun provideFeatureExtractionManager(
        audioPreprocessor: AudioPreprocessor,
        melSpectrogramGenerator: MelSpectrogramGenerator,
        mfccGenerator: MFCCGenerator,
        logger: Logger
    ): FeatureExtractionManager {
        return FeatureExtractionManager(audioPreprocessor, melSpectrogramGenerator, mfccGenerator, logger)
    }
}
