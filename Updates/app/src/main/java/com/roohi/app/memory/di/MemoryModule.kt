package com.roohi.app.memory.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.memory.data.MemoryDatabase
import com.roohi.app.memory.data.MemoryDao
import com.roohi.app.memory.data.MemoryRepositoryImpl
import com.roohi.app.memory.domain.MemoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MemoryModule {

    @Provides
    @Singleton
    fun provideMemoryDatabase(@ApplicationContext context: Context): MemoryDatabase {
        return Room.databaseBuilder(
            context,
            MemoryDatabase::class.java,
            "roohi_memory_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMemoryDao(database: MemoryDatabase): MemoryDao {
        return database.memoryDao()
    }

    @Provides
    @Singleton
    fun provideMemoryRepository(impl: MemoryRepositoryImpl): MemoryRepository {
        return impl
    }
}
