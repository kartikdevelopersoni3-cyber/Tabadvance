package com.roohi.app.voiceauth.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roohi.app.voiceauth.data.local.dao.VoiceprintDao
import com.roohi.app.voiceauth.data.local.entity.VoiceprintEntity

@Database(
    entities = [VoiceprintEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VoiceDatabase : RoomDatabase() {
    abstract fun voiceprintDao(): VoiceprintDao
}
