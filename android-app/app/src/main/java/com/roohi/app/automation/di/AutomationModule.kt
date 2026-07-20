package com.roohi.app.automation.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.automation.data.AutomationDao
import com.roohi.app.automation.data.AutomationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AutomationModule {

    @Provides
    @Singleton
    fun provideAutomationDatabase(@ApplicationContext context: Context): AutomationDatabase {
        return Room.databaseBuilder(
            context,
            AutomationDatabase::class.java,
            "automation_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAutomationDao(db: AutomationDatabase): AutomationDao = db.automationDao()
}
