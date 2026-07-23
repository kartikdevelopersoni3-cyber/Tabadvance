package com.roohi.app.speech.di

import android.content.Context
import com.roohi.app.core.logging.Logger
import com.roohi.app.speech.data.provider.AndroidSpeechProvider
import com.roohi.app.speech.data.repository.SpeechRecognitionRepositoryImpl
import com.roohi.app.speech.domain.SpeechRecognitionManager
import com.roohi.app.speech.domain.SpeechRecognitionProvider
import com.roohi.app.speech.domain.SpeechRecognitionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpeechModule {

    @Provides
    @Singleton
    fun provideSpeechRecognitionRepository(): SpeechRecognitionRepository {
        return SpeechRecognitionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSpeechRecognitionProvider(
        @ApplicationContext context: Context,
        logger: Logger
    ): SpeechRecognitionProvider {
        return AndroidSpeechProvider(context, logger)
    }

    @Provides
    @Singleton
    fun provideSpeechRecognitionManager(
        provider: SpeechRecognitionProvider,
        repository: SpeechRecognitionRepository,
        logger: Logger
    ): SpeechRecognitionManager {
        return SpeechRecognitionManager(provider, repository, logger)
    }

    @Provides
    @Singleton
    fun provideTextToSpeechManager(
        @ApplicationContext context: Context,
        logger: Logger
    ): com.roohi.app.speech.domain.TextToSpeechManager {
        return com.roohi.app.speech.domain.TextToSpeechManager(context, logger)
    }
}
