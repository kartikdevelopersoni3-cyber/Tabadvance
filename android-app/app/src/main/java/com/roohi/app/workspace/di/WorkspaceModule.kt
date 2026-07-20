package com.roohi.app.workspace.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.workspace.data.WorkspaceDao
import com.roohi.app.workspace.data.WorkspaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkspaceModule {

    @Provides
    @Singleton
    fun provideWorkspaceDatabase(@ApplicationContext context: Context): WorkspaceDatabase {
        return Room.databaseBuilder(
            context,
            WorkspaceDatabase::class.java,
            "workspace_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideWorkspaceDao(db: WorkspaceDatabase): WorkspaceDao = db.workspaceDao()
}
