package com.roohi.app.automation.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "workflows")
data class WorkflowEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val triggerCondition: String,
    val actionPayload: String,
    val isEnabled: Boolean = true
)

@Entity(tableName = "automation_history")
data class ExecutionHistoryEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val workflowId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val success: Boolean,
    val failureReason: String? = null
)

@Dao
interface AutomationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkflow(entity: WorkflowEntity)

    @Query("SELECT * FROM workflows WHERE isEnabled = 1")
    suspend fun getActiveWorkflows(): List<WorkflowEntity>
    
    @Query("SELECT * FROM workflows WHERE id = :id")
    suspend fun getWorkflowById(id: String): WorkflowEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExecutionHistory(entity: ExecutionHistoryEntity)
}

@Database(entities = [WorkflowEntity::class, ExecutionHistoryEntity::class], version = 1, exportSchema = false)
abstract class AutomationDatabase : RoomDatabase() {
    abstract fun automationDao(): AutomationDao
}
