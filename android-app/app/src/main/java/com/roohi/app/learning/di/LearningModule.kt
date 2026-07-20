package com.roohi.app.learning.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.learning.data.LearningDao
import com.roohi.app.learning.data.LearningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LearningModule {

    @Provides
    @Singleton
    fun provideLearningDatabase(@ApplicationContext context: Context): LearningDatabase {
        return Room.databaseBuilder(
            context,
            LearningDatabase::class.java,
            "learning_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideLearningDao(db: LearningDatabase): LearningDao = db.learningDao()
}
