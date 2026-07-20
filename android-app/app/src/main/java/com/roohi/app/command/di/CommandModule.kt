package com.roohi.app.command.di

import com.roohi.app.command.data.CommandRepositoryImpl
import com.roohi.app.command.domain.CommandRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommandModule {
    @Provides
    @Singleton
    fun provideCommandRepository(impl: CommandRepositoryImpl): CommandRepository {
        return impl
    }
}
