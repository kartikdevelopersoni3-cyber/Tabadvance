package com.roohi.app.evolution.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EvolutionModule {
    // Provides hooks for local source parsing and bytecode limits safely explicitly offline.
}
