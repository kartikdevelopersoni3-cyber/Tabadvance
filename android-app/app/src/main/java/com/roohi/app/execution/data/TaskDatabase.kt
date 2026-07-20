package com.roohi.app.execution.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class, WorkflowEntity::class, ExecutionHistoryEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun workflowDao(): WorkflowDao
    abstract fun executionHistoryDao(): ExecutionHistoryDao
}
