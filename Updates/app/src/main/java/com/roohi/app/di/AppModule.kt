package com.roohi.app.di

import android.content.Context
import com.roohi.app.core.logging.Logger
import com.roohi.app.core.logging.RoohiLogger
import com.roohi.app.core.permissions.PermissionsManager
import com.roohi.app.core.settings.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return RoohiLogger()
    }

    @Provides
    @Singleton
    fun provideSettingsManager(@ApplicationContext context: Context): SettingsManager {
        return SettingsManager(context)
    }
    
    @Provides
    @Singleton
    fun providePermissionsManager(): PermissionsManager {
        return PermissionsManager()
    }
}
