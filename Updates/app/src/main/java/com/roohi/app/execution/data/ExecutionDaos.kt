package com.roohi.app.execution.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExecutionHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(entity: ExecutionHistoryEntity)

    @Query("SELECT * FROM execution_history ORDER BY executionTimestamp DESC LIMIT 100")
    suspend fun getRecentHistory(): List<ExecutionHistoryEntity>
}

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE status = :status ORDER BY createdTimestamp ASC")
    suspend fun getTasksByStatus(status: String): List<TaskEntity>

    @Query("UPDATE tasks SET status = :status WHERE id = :id")
    suspend fun updateTaskStatus(id: String, status: String)
}

@Dao
interface WorkflowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkflow(workflow: WorkflowEntity)

    @Query("SELECT * FROM workflows WHERE status = :status LIMIT 1")
    suspend fun getActiveWorkflow(status: String = "ACTIVE"): WorkflowEntity?

    @Query("UPDATE workflows SET status = :status, currentStepIndex = :stepIndex WHERE id = :id")
    suspend fun updateWorkflowProgress(id: String, status: String, stepIndex: Int)
}
