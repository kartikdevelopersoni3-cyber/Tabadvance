package com.roohi.app.identity.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "emergency_contacts")
data class EmergencyContactEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "relationship") val relationship: String,
    @ColumnInfo(name = "is_primary") val isPrimary: Boolean,
    @ColumnInfo(name = "is_medical") val isMedical: Boolean,
    @ColumnInfo(name = "metadata") val metadata: String
)
