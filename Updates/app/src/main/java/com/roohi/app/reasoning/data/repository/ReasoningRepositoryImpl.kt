package com.roohi.app.reasoning.data.repository

import com.roohi.app.reasoning.data.local.dao.ReasoningDao
import com.roohi.app.reasoning.data.local.entity.GoalEntity
import com.roohi.app.reasoning.data.local.entity.PlanEntity
import com.roohi.app.reasoning.domain.ReasoningRepository
import com.roohi.app.reasoning.domain.models.Goal
import com.roohi.app.reasoning.domain.models.GoalStatus
import com.roohi.app.reasoning.domain.models.Plan
import com.roohi.app.reasoning.domain.models.PlanStatus
import com.roohi.app.reasoning.domain.models.PlanStep
import com.roohi.app.reasoning.domain.models.StepStatus
import javax.inject.Inject
import javax.inject.Singleton
import org.json.JSONArray
import org.json.JSONObject

@Singleton
class ReasoningRepositoryImpl @Inject constructor(
    private val dao: ReasoningDao
) : ReasoningRepository {
    override suspend fun saveGoal(goal: Goal) {
        dao.insertGoal(GoalEntity(goal.id, goal.description, goal.priority, goal.status.name, goal.createdAt, goal.updatedAt))
    }

    override suspend fun getActiveGoals(): List<Goal> {
        return dao.getActiveGoals().map { Goal(it.id, it.description, it.priority, GoalStatus.valueOf(it.status), it.createdAt, it.updatedAt) }
    }

    override suspend fun getGoal(id: String): Goal? {
        return dao.getGoalById(id)?.let { Goal(it.id, it.description, it.priority, GoalStatus.valueOf(it.status), it.createdAt, it.updatedAt) }
    }

    override suspend fun savePlan(plan: Plan) {
        val stepsArray = JSONArray()
        plan.steps.forEach { step ->
            val stepObj = JSONObject()
            stepObj.put("id", step.id)
            stepObj.put("description", step.description)
            stepObj.put("status", step.status.name)
            stepObj.put("dependencies", JSONArray(step.dependencies))
            stepsArray.put(stepObj)
        }
        dao.insertPlan(PlanEntity(plan.id, plan.goalId, stepsArray.toString(), plan.status.name, plan.createdAt))
    }

    override suspend fun getPlansForGoal(goalId: String): List<Plan> {
        return dao.getPlansForGoal(goalId).map { entity ->
            val stepsList = mutableListOf<PlanStep>()
            try {
                val array = JSONArray(entity.stepsJson)
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    val depsArray = obj.getJSONArray("dependencies")
                    val depsList = mutableListOf<String>()
                    for (j in 0 until depsArray.length()) depsList.add(depsArray.getString(j))
                    stepsList.add(PlanStep(obj.getString("id"), obj.getString("description"), StepStatus.valueOf(obj.getString("status")), depsList))
                }
            } catch (e: Exception) {}
            Plan(entity.id, entity.goalId, stepsList, PlanStatus.valueOf(entity.status), entity.createdAt)
        }
    }
}
