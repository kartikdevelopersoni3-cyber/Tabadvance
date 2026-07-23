package com.roohi.app.identity.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "device_metadata")
data class DeviceMetadataEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "installation_id") val installationId: String,
    @ColumnInfo(name = "device_id") val deviceId: String,
    @ColumnInfo(name = "tablet_metadata") val tabletMetadata: String,
    @ColumnInfo(name = "android_version") val androidVersion: String,
    @ColumnInfo(name = "build_version") val buildVersion: String,
    @ColumnInfo(name = "registration_timestamp") val registrationTimestamp: Long,
    @ColumnInfo(name = "last_validation_timestamp") val lastValidationTimestamp: Long
)
