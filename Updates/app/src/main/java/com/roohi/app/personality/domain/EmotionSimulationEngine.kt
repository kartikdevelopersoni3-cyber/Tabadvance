package com.roohi.app.personality.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.personality.domain.models.SimulatedEmotion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmotionSimulationEngine @Inject constructor(
    private val logger: Logger
) {
    private val _currentState = MutableStateFlow(SimulatedEmotion.NEUTRAL)
    val currentState: StateFlow<SimulatedEmotion> = _currentState.asStateFlow()

    fun transitionTo(emotion: SimulatedEmotion, reason: String) {
        logger.i("EmotionSimulationEngine", "Simulating emotion shift: $emotion | Reason: $reason")
        _currentState.value = emotion
    }
    
    fun getEmotionContext(): String {
        return "Simulated Emotion: ${_currentState.value.name}"
    }

    fun reset() {
        _currentState.value = SimulatedEmotion.NEUTRAL
    }
}
