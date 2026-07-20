package com.roohi.app.execution.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.command.domain.CommandExecutionManager
import com.roohi.app.core.logging.Logger
import com.roohi.app.execution.data.ExecutionHistoryDao
import com.roohi.app.execution.data.TaskDao
import com.roohi.app.execution.data.TaskDatabase
import com.roohi.app.execution.data.WorkflowDao
import com.roohi.app.execution.domain.ActionQueueManager
import com.roohi.app.execution.domain.ApprovalManager
import com.roohi.app.execution.domain.ExecutionHistoryManager
import com.roohi.app.execution.domain.ExecutionPlanner
import com.roohi.app.execution.domain.RollbackEngine
import com.roohi.app.execution.domain.TaskManager
import com.roohi.app.execution.domain.TaskPersistenceManager
import com.roohi.app.execution.domain.TaskRecoveryEngine
import com.roohi.app.execution.domain.TaskScheduler
import com.roohi.app.execution.domain.WorkflowEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExecutionModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "tasks_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()

    @Provides
    fun provideWorkflowDao(db: TaskDatabase): WorkflowDao = db.workflowDao()

    @Provides
    fun provideExecutionHistoryDao(db: TaskDatabase): ExecutionHistoryDao = db.executionHistoryDao()

    // Domain models are provided by @Inject constructor, except when we need explicit bindings,
    // but @Inject constructor works automatically in Hilt for normal classes.
}
