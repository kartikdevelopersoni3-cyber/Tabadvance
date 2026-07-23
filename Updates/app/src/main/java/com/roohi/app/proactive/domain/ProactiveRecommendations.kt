package com.roohi.app.proactive.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.proactive.data.ProactiveDao
import com.roohi.app.proactive.data.RecommendationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionEngine @Inject constructor(private val logger: Logger) {
    fun generateSuggestion(predictionType: String): String {
        logger.d("SuggestionEngine", "Generating suggestion for $predictionType")
        return "Would you like me to assist with $predictionType?"
    }
}

@Singleton
class RecommendationEngine @Inject constructor(
    private val proactiveDao: ProactiveDao,
    private val suggestionEngine: SuggestionEngine,
    private val logger: Logger
) {
    suspend fun processPrediction(predictionType: String, confidenceScore: Float): String? {
        if (confidenceScore > 0.8f) {
            val rec = suggestionEngine.generateSuggestion(predictionType)
            proactiveDao.insertRecommendation(
                RecommendationEntity(
                    recommendationType = predictionType,
                    recommendationText = rec,
                    confidenceScore = confidenceScore
                )
            )
            return rec
        }
        return null
    }
}
