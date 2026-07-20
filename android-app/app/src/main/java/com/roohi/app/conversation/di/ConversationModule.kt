package com.roohi.app.conversation.di

import com.roohi.app.core.logging.Logger
import com.roohi.app.conversation.data.ConversationRepositoryImpl
import com.roohi.app.conversation.domain.ContextManager
import com.roohi.app.conversation.domain.ConversationManager
import com.roohi.app.conversation.domain.ConversationRepository
import com.roohi.app.conversation.domain.FallbackEngine
import com.roohi.app.conversation.domain.IntentClassifier
import com.roohi.app.conversation.domain.ResponseGenerator
import com.roohi.app.command.domain.CommandExecutionManager
import com.roohi.app.memory.domain.MemoryManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConversationModule {

    @Provides
    @Singleton
    fun provideConversationRepository(impl: ConversationRepositoryImpl): ConversationRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideContextManager(logger: Logger): ContextManager {
        return ContextManager(logger)
    }

    @Provides
    @Singleton
    fun provideIntentClassifier(): IntentClassifier {
        return IntentClassifier()
    }

    @Provides
    @Singleton
    fun provideFallbackEngine(): FallbackEngine {
        return FallbackEngine()
    }

    @Provides
    @Singleton
    fun provideResponseGenerator(): ResponseGenerator {
        return ResponseGenerator()
    }

    @Provides
    @Singleton
    fun provideConversationManager(
        repository: ConversationRepository,
        contextManager: ContextManager,
        intentClassifier: IntentClassifier,
        responseGenerator: ResponseGenerator,
        fallbackEngine: FallbackEngine,
        commandExecutionManager: CommandExecutionManager,
        memoryManager: MemoryManager,
        textToSpeechManager: com.roohi.app.speech.domain.TextToSpeechManager,
        reasoningManager: com.roohi.app.reasoning.domain.ReasoningManager,
        personalityManager: com.roohi.app.personality.domain.PersonalityManager,
        logger: Logger
    ): ConversationManager {
        return ConversationManager(
            repository,
            contextManager,
            intentClassifier,
            responseGenerator,
            fallbackEngine,
            commandExecutionManager,
            memoryManager,
            textToSpeechManager,
            reasoningManager,
            personalityManager,
            logger
        )
    }
}
