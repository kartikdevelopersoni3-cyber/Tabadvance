package com.roohi.app.owner.di

import android.content.Context
import com.roohi.app.core.logging.Logger
import com.roohi.app.owner.data.AvatarStorageLayer
import com.roohi.app.owner.data.CharacterRepository
import com.roohi.app.owner.data.OwnerSettingsRepository
import com.roohi.app.owner.data.ThemeRepository
import com.roohi.app.owner.domain.ApiConfigurationManager
import com.roohi.app.owner.domain.AvatarManager
import com.roohi.app.owner.domain.OwnerConfigurationManager
import com.roohi.app.owner.domain.OwnerSecurityManager
import com.roohi.app.owner.domain.PermissionSetupManager
import com.roohi.app.owner.domain.RoohiCharacterManager
import com.roohi.app.owner.domain.VoiceConfigurationManager
import com.roohi.app.owner.monitor.APIValidator
import com.roohi.app.owner.monitor.AvatarValidator
import com.roohi.app.owner.monitor.CharacterValidator
import com.roohi.app.owner.monitor.OwnerHealthMonitor
import com.roohi.app.owner.monitor.PermissionValidator
import com.roohi.app.speech.domain.TextToSpeechManager
import com.roohi.app.voiceauth.domain.VoiceAuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OwnerModule {

    @Provides
    @Singleton
    fun provideOwnerSettingsRepository(logger: Logger): OwnerSettingsRepository = OwnerSettingsRepository(logger)

    @Provides
    @Singleton
    fun provideCharacterRepository(logger: Logger): CharacterRepository = CharacterRepository(logger)

    @Provides
    @Singleton
    fun provideThemeRepository(logger: Logger): ThemeRepository = ThemeRepository(logger)

    @Provides
    @Singleton
    fun provideAvatarStorageLayer(logger: Logger): AvatarStorageLayer = AvatarStorageLayer(logger)

    @Provides
    @Singleton
    fun provideOwnerConfigurationManager(repo: OwnerSettingsRepository, logger: Logger): OwnerConfigurationManager = OwnerConfigurationManager(repo, logger)

    @Provides
    @Singleton
    fun provideRoohiCharacterManager(repo: CharacterRepository, logger: Logger): RoohiCharacterManager = RoohiCharacterManager(repo, logger)

    @Provides
    @Singleton
    fun provideAvatarManager(storage: AvatarStorageLayer, themeRepo: ThemeRepository, logger: Logger): AvatarManager = AvatarManager(storage, themeRepo, logger)

    @Provides
    @Singleton
    fun provideVoiceConfigurationManager(tts: TextToSpeechManager, voiceAuth: VoiceAuthManager, logger: Logger): VoiceConfigurationManager = VoiceConfigurationManager(tts, voiceAuth, logger)

    @Provides
    @Singleton
    fun provideApiConfigurationManager(logger: Logger): ApiConfigurationManager = ApiConfigurationManager(logger)

    @Provides
    @Singleton
    fun providePermissionSetupManager(@ApplicationContext context: Context, logger: Logger): PermissionSetupManager = PermissionSetupManager(context, logger)

    @Provides
    @Singleton
    fun provideOwnerSecurityManager(logger: Logger): OwnerSecurityManager = OwnerSecurityManager(logger)

    @Provides
    @Singleton
    fun provideOwnerHealthMonitor(manager: OwnerConfigurationManager, logger: Logger): OwnerHealthMonitor = OwnerHealthMonitor(manager, logger)

    @Provides
    @Singleton
    fun provideCharacterValidator(manager: RoohiCharacterManager, logger: Logger): CharacterValidator = CharacterValidator(manager, logger)

    @Provides
    @Singleton
    fun provideAvatarValidator(manager: AvatarManager, logger: Logger): AvatarValidator = AvatarValidator(manager, logger)

    @Provides
    @Singleton
    fun provideAPIValidator(manager: ApiConfigurationManager, logger: Logger): APIValidator = APIValidator(manager, logger)

    @Provides
    @Singleton
    fun providePermissionValidator(manager: PermissionSetupManager, logger: Logger): PermissionValidator = PermissionValidator(manager, logger)
}
  
