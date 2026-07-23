package com.roohi.app.coordination.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoordinationModule {
    // All coordination components use @Inject constructor and exist independently 
    // or depend on externally bounded logic cleanly avoiding explicit provide methods where implicit is safe.
}
