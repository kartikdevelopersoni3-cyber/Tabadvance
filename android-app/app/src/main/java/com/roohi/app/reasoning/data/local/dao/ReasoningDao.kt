package com.roohi.app.reasoning.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.roohi.app.reasoning.data.local.entity.GoalEntity
import com.roohi.app.reasoning.data.local.entity.PlanEntity

@Dao
interface ReasoningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Query("SELECT * FROM goals WHERE status = 'ACTIVE' ORDER BY priority DESC")
    suspend fun getActiveGoals(): List<GoalEntity>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: String): GoalEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: PlanEntity)

    @Update
    suspend fun updatePlan(plan: PlanEntity)

    @Query("SELECT * FROM plans WHERE goal_id = :goalId")
    suspend fun getPlansForGoal(goalId: String): List<PlanEntity>
}
