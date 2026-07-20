package com.roohi.app.vision.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "visual_memory")
data class VisualMemoryEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val contextSummary: String,
    val ocrContent: String,
    val screenState: String,
    val isEmergency: Boolean = false
)

@Dao
interface VisionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisualMemory(entity: VisualMemoryEntity)

    @Query("SELECT * FROM visual_memory ORDER BY timestamp DESC LIMIT 50")
    suspend fun getRecentVisualMemories(): List<VisualMemoryEntity>
    
    @Query("SELECT * FROM visual_memory WHERE isEmergency = 1 ORDER BY timestamp DESC LIMIT 10")
    suspend fun getEmergencyVisualMemories(): List<VisualMemoryEntity>
}

@Database(entities = [VisualMemoryEntity::class], version = 1, exportSchema = false)
abstract class VisionDatabase : RoomDatabase() {
    abstract fun visionDao(): VisionDao
}
