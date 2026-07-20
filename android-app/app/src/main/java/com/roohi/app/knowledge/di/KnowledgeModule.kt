package com.roohi.app.knowledge.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.knowledge.data.KnowledgeDao
import com.roohi.app.knowledge.data.KnowledgeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KnowledgeModule {

    @Provides
    @Singleton
    fun provideKnowledgeDatabase(@ApplicationContext context: Context): KnowledgeDatabase {
        return Room.databaseBuilder(
            context,
            KnowledgeDatabase::class.java,
            "knowledge_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideKnowledgeDao(db: KnowledgeDatabase): KnowledgeDao = db.knowledgeDao()
}
