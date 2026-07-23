package com.roohi.app.learning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.learning.data.LearningDao
import com.roohi.app.learning.data.LearningEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningManager @Inject constructor(
    private val learningDao: LearningDao,
    private val habitAnalyzer: HabitAnalyzer,
    private val usagePatternEngine: UsagePatternEngine,
    private val preferenceLearningEngine: PreferenceLearningEngine,
    private val scheduleLearningEngine: ScheduleLearningEngine,
    private val commandLearningEngine: CommandLearningEngine,
    private val communicationStyleAnalyzer: CommunicationStyleAnalyzer,
    private val logger: Logger
) {
    suspend fun observeBehavior(sourceModule: String, patternType: String, patternData: String) {
        logger.i("LearningManager", "Observing behavior from $sourceModule: $patternType")
        val score = habitAnalyzer.analyzeHabit(patternData)
        val entity = LearningEntity(
            patternType = patternType,
            patternData = patternData,
            confidenceScore = score,
            sourceModule = sourceModule
        )
        learningDao.insertPattern(entity)
    }

    suspend fun getAdaptiveContext(): String {
        val patterns = learningDao.getActivePatterns()
        return if (patterns.isNotEmpty()) "Adaptive Context Applied" else "Default Context"
    }
}
