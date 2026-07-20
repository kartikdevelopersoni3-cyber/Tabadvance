package com.roohi.app.proactive.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "proactive_events")
data class ProactiveEventEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val eventType: String,
    val contextData: String,
    val predictionScore: Float,
    val confidenceScore: Float,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val lastTriggeredAt: Long = System.currentTimeMillis(),
    val triggerCount: Int = 1,
    val status: String = "ACTIVE"
)

@Entity(tableName = "proactive_recommendations")
data class RecommendationEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val recommendationType: String,
    val recommendationText: String,
    val confidenceScore: Float,
    val acceptedCount: Int = 0,
    val rejectedCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Dao
interface ProactiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(entity: ProactiveEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(entity: RecommendationEntity)

    @Query("SELECT * FROM proactive_events WHERE eventType = :type")
    suspend fun getEventsByType(type: String): List<ProactiveEventEntity>
    
    @Query("SELECT * FROM proactive_recommendations WHERE confidenceScore > :threshold ORDER BY confidenceScore DESC LIMIT 10")
    suspend fun getHighConfidenceRecommendations(threshold: Float): List<RecommendationEntity>
}

@Database(entities = [ProactiveEventEntity::class, RecommendationEntity::class], version = 1, exportSchema = false)
abstract class ProactiveDatabase : RoomDatabase() {
    abstract fun proactiveDao(): ProactiveDao
}
