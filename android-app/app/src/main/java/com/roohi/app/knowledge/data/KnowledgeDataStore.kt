package com.roohi.app.knowledge.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "knowledge_nodes")
data class KnowledgeEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val nodeType: String,
    val nodeValue: String,
    val confidenceScore: Float,
    val sourceModule: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val lastUsedAt: Long = System.currentTimeMillis(),
    val usageCount: Int = 1,
    val isActive: Boolean = true
)

@Entity(tableName = "knowledge_relationships")
data class RelationshipEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val sourceNode: String,
    val targetNode: String,
    val relationshipType: String,
    val confidenceScore: Float,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Dao
interface KnowledgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(entity: KnowledgeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelationship(entity: RelationshipEntity)
    
    @Query("SELECT * FROM knowledge_nodes WHERE nodeValue LIKE :query")
    suspend fun searchNodes(query: String): List<KnowledgeEntity>
    
    @Query("SELECT * FROM knowledge_relationships WHERE sourceNode = :nodeId OR targetNode = :nodeId")
    suspend fun getNodeRelationships(nodeId: String): List<RelationshipEntity>
}

@Database(entities = [KnowledgeEntity::class, RelationshipEntity::class], version = 1, exportSchema = false)
abstract class KnowledgeDatabase : RoomDatabase() {
    abstract fun knowledgeDao(): KnowledgeDao
}
