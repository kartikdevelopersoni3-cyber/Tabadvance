package com.roohi.app.wakeword.di

import com.roohi.app.core.logging.Logger
import com.roohi.app.core.ml.InterpreterProvider
import com.roohi.app.voiceauth.domain.VoiceAuthManager
import com.roohi.app.wakeword.data.audio.AudioFocusManager
import com.roohi.app.wakeword.data.audio.AudioRecorderFlow
import com.roohi.app.wakeword.data.engine.ContinuousWakeWordEngine
import com.roohi.app.wakeword.domain.WakeWordEngine
import com.roohi.app.wakeword.domain.WakeWordManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WakeWordModule {

    @Provides
    @Singleton
    fun provideAudioRecorderFlow(logger: Logger, audioFocusManager: AudioFocusManager): AudioRecorderFlow {
        return AudioRecorderFlow(logger, audioFocusManager)
    }

    @Provides
    @Singleton
    fun provideWakeWordEngine(interpreterProvider: InterpreterProvider, logger: Logger): WakeWordEngine {
        return ContinuousWakeWordEngine(interpreterProvider, logger)
    }

    @Provides
    @Singleton
    fun provideWakeWordManager(
        audioRecorder: AudioRecorderFlow,
        engine: WakeWordEngine,
        voiceAuthManager: VoiceAuthManager,
        logger: Logger
    ): WakeWordManager {
        return WakeWordManager(audioRecorder, engine, voiceAuthManager, logger)
    }
}
