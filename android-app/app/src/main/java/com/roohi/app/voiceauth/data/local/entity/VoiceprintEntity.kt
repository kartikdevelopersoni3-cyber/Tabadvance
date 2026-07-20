package com.roohi.app.voiceauth.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "owner_voiceprints")
data class VoiceprintEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "owner_name") val ownerName: String,
    @ColumnInfo(name = "embedding_data") val embeddingData: String, // Stored as comma separated string or JSON
    @ColumnInfo(name = "enrolled_timestamp") val enrolledTimestamp: Long
)
