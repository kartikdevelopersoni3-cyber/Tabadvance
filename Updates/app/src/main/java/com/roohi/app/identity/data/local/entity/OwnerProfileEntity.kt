package com.roohi.app.identity.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "owner_profile")
data class OwnerProfileEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "owner_name") val ownerName: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "emergency_preferences") val emergencyPreferences: String,
    @ColumnInfo(name = "accessibility_preferences") val accessibilityPreferences: String,
    @ColumnInfo(name = "tablet_preferences") val tabletPreferences: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Long
)
