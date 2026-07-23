package com.roohi.app.learning.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "learning_patterns")
data class LearningEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val patternType: String,
    val patternData: String,
    val confidenceScore: Float,
    val sourceModule: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val lastUsedAt: Long = System.currentTimeMillis(),
    val usageCount: Int = 1,
    val isActive: Boolean = true
)

@Dao
interface LearningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPattern(entity: LearningEntity)

    @Query("SELECT * FROM learning_patterns WHERE isActive = 1 ORDER BY confidenceScore DESC")
    suspend fun getActivePatterns(): List<LearningEntity>

    @Query("SELECT * FROM learning_patterns WHERE patternType = :type")
    suspend fun getPatternsByType(type: String): List<LearningEntity>
}

@Database(entities = [LearningEntity::class], version = 1, exportSchema = false)
abstract class LearningDatabase : RoomDatabase() {
    abstract fun learningDao(): LearningDao
}
