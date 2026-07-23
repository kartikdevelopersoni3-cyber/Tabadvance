package com.roohi.app.personality.di

import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.OwnerProfileManager
import com.roohi.app.memory.domain.MemoryRetrievalEngine
import com.roohi.app.personality.domain.ConversationStyleEngine
import com.roohi.app.personality.domain.EmotionSimulationEngine
import com.roohi.app.personality.domain.OwnerRelationshipEngine
import com.roohi.app.personality.domain.PersonalityManager
import com.roohi.app.personality.domain.PersonalityMemoryBridge
import com.roohi.app.personality.domain.TabletPresenceManager
import com.roohi.app.personality.monitor.ConversationConsistencyMonitor
import com.roohi.app.personality.monitor.EmotionStateValidator
import com.roohi.app.personality.monitor.PersonalityHealthMonitor
import com.roohi.app.personality.monitor.PersonalityRecoveryEngine
import com.roohi.app.personality.monitor.PersonalityValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonalityModule {

    @Provides
    @Singleton
    fun provideEmotionSimulationEngine(logger: Logger): EmotionSimulationEngine {
        return EmotionSimulationEngine(logger)
    }

    @Provides
    @Singleton
    fun provideOwnerRelationshipEngine(
        ownerProfileManager: OwnerProfileManager,
        memoryRetrievalEngine: MemoryRetrievalEngine,
        logger: Logger
    ): OwnerRelationshipEngine {
        return OwnerRelationshipEngine(ownerProfileManager, memoryRetrievalEngine, logger)
    }

    @Provides
    @Singleton
    fun provideConversationStyleEngine(logger: Logger): ConversationStyleEngine {
        return ConversationStyleEngine(logger)
    }

    @Provides
    @Singleton
    fun providePersonalityMemoryBridge(logger: Logger): PersonalityMemoryBridge {
        return PersonalityMemoryBridge(logger)
    }

    @Provides
    @Singleton
    fun provideTabletPresenceManager(logger: Logger): TabletPresenceManager {
        return TabletPresenceManager(logger)
    }

    @Provides
    @Singleton
    fun providePersonalityManager(
        emotionSimulationEngine: EmotionSimulationEngine,
        ownerRelationshipEngine: OwnerRelationshipEngine,
        conversationStyleEngine: ConversationStyleEngine,
        personalityMemoryBridge: PersonalityMemoryBridge,
        tabletPresenceManager: TabletPresenceManager,
        logger: Logger
    ): PersonalityManager {
        return PersonalityManager(
            emotionSimulationEngine,
            ownerRelationshipEngine,
            conversationStyleEngine,
            personalityMemoryBridge,
            tabletPresenceManager,
            logger
        )
    }

    @Provides
    @Singleton
    fun provideEmotionStateValidator(logger: Logger): EmotionStateValidator {
        return EmotionStateValidator(logger)
    }

    @Provides
    @Singleton
    fun providePersonalityValidator(
        emotionStateValidator: EmotionStateValidator,
        logger: Logger
    ): PersonalityValidator {
        return PersonalityValidator(emotionStateValidator, logger)
    }

    @Provides
    @Singleton
    fun providePersonalityRecoveryEngine(
        emotionSimulationEngine: EmotionSimulationEngine,
        logger: Logger
    ): PersonalityRecoveryEngine {
        return PersonalityRecoveryEngine(emotionSimulationEngine, logger)
    }

    @Provides
    @Singleton
    fun providePersonalityHealthMonitor(
        personalityValidator: PersonalityValidator,
        personalityRecoveryEngine: PersonalityRecoveryEngine,
        logger: Logger
    ): PersonalityHealthMonitor {
        return PersonalityHealthMonitor(personalityValidator, personalityRecoveryEngine, logger)
    }

    @Provides
    @Singleton
    fun provideConversationConsistencyMonitor(logger: Logger): ConversationConsistencyMonitor {
        return ConversationConsistencyMonitor(logger)
    }
}
