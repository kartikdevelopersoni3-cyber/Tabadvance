package com.roohi.app.voiceauth.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.core.logging.Logger
import com.roohi.app.core.ml.InterpreterProvider
import com.roohi.app.core.security.EncryptionManager
import com.roohi.app.voiceauth.data.engine.TFLiteSpeakerVerificationEngine
import com.roohi.app.voiceauth.data.local.VoiceDatabase
import com.roohi.app.voiceauth.data.local.dao.VoiceprintDao
import com.roohi.app.voiceauth.data.repository.VoiceProfileRepositoryImpl
import com.roohi.app.voiceauth.domain.SpeakerVerificationEngine
import com.roohi.app.voiceauth.domain.VoiceAuthManager
import com.roohi.app.voiceauth.domain.VoiceProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceAuthModule {

    @Provides
    @Singleton
    fun provideVoiceDatabase(@ApplicationContext context: Context): VoiceDatabase {
        return Room.databaseBuilder(
            context,
            VoiceDatabase::class.java,
            "roohi_voice_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideVoiceprintDao(database: VoiceDatabase): VoiceprintDao {
        return database.voiceprintDao()
    }

    @Provides
    @Singleton
    fun provideVoiceProfileRepository(dao: VoiceprintDao, encryptionManager: EncryptionManager): VoiceProfileRepository {
        return VoiceProfileRepositoryImpl(dao, encryptionManager)
    }

    @Provides
    @Singleton
    fun provideSpeakerVerificationEngine(interpreterProvider: InterpreterProvider, logger: Logger): SpeakerVerificationEngine {
        return TFLiteSpeakerVerificationEngine(interpreterProvider, logger)
    }

    @Provides
    @Singleton
    fun provideVoiceAuthManager(
        repository: VoiceProfileRepository,
        engine: SpeakerVerificationEngine,
        logger: Logger
    ): VoiceAuthManager {
        return VoiceAuthManager(repository, engine, logger)
    }
}
