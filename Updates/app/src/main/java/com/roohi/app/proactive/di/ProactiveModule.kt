package com.roohi.app.proactive.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.proactive.data.ProactiveDao
import com.roohi.app.proactive.data.ProactiveDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProactiveModule {

    @Provides
    @Singleton
    fun provideProactiveDatabase(@ApplicationContext context: Context): ProactiveDatabase {
        return Room.databaseBuilder(
            context,
            ProactiveDatabase::class.java,
            "proactive_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideProactiveDao(db: ProactiveDatabase): ProactiveDao = db.proactiveDao()
}
