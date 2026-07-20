package com.roohi.app.reasoning.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roohi.app.reasoning.data.local.dao.ReasoningDao
import com.roohi.app.reasoning.data.local.entity.GoalEntity
import com.roohi.app.reasoning.data.local.entity.PlanEntity

@Database(entities = [GoalEntity::class, PlanEntity::class], version = 1, exportSchema = false)
abstract class ReasoningDatabase : RoomDatabase() {
    abstract fun reasoningDao(): ReasoningDao
}
