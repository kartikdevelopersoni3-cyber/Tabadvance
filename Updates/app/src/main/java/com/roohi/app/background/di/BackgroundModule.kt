package com.roohi.app.background.di

import android.content.Context
import com.roohi.app.background.battery.BatteryOptimizationHelper
import com.roohi.app.background.monitor.ServiceHealthMonitor
import com.roohi.app.core.logging.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BackgroundModule {

    @Provides
    @Singleton
    fun provideBatteryOptimizationHelper(): BatteryOptimizationHelper {
        return BatteryOptimizationHelper()
    }

    @Provides
    @Singleton
    fun provideServiceHealthMonitor(
        @ApplicationContext context: Context,
        logger: Logger
    ): ServiceHealthMonitor {
        return ServiceHealthMonitor(context, logger)
    }
}
