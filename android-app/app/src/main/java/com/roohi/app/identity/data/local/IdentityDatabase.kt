package com.roohi.app.identity.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roohi.app.identity.data.local.dao.IdentityDao
import com.roohi.app.identity.data.local.entity.DeviceMetadataEntity
import com.roohi.app.identity.data.local.entity.EmergencyContactEntity
import com.roohi.app.identity.data.local.entity.OwnerProfileEntity

@Database(
    entities = [
        OwnerProfileEntity::class,
        EmergencyContactEntity::class,
        DeviceMetadataEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class IdentityDatabase : RoomDatabase() {
    abstract fun identityDao(): IdentityDao
}
