package com.roohi.app.reasoning.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.reasoning.data.local.ReasoningDatabase
import com.roohi.app.reasoning.data.local.dao.ReasoningDao
import com.roohi.app.reasoning.data.repository.ReasoningRepositoryImpl
import com.roohi.app.reasoning.domain.ReasoningRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReasoningModule {

    @Provides
    @Singleton
    fun provideReasoningDatabase(@ApplicationContext context: Context): ReasoningDatabase {
        return Room.databaseBuilder(
            context,
            ReasoningDatabase::class.java,
            "roohi_reasoning_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideReasoningDao(database: ReasoningDatabase): ReasoningDao {
        return database.reasoningDao()
    }

    @Provides
    @Singleton
    fun provideReasoningRepository(dao: ReasoningDao): ReasoningRepository {
        return ReasoningRepositoryImpl(dao)
    }
}
