package com.roohi.app.workspace.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "workspaces")
data class WorkspaceEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val contextType: String,
    val isActive: Boolean = true
)

@Dao
interface WorkspaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkspace(entity: WorkspaceEntity)

    @Query("SELECT * FROM workspaces WHERE isActive = 1")
    suspend fun getActiveWorkspaces(): List<WorkspaceEntity>
}

@Database(entities = [WorkspaceEntity::class], version = 1, exportSchema = false)
abstract class WorkspaceDatabase : RoomDatabase() {
    abstract fun workspaceDao(): WorkspaceDao
}
