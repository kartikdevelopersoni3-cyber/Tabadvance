package com.roohi.app.voiceauth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roohi.app.core.logging.Logger
import com.roohi.app.voiceauth.domain.SpeakerVerificationEngine
import com.roohi.app.voiceauth.domain.VoiceProfileRepository
import com.roohi.app.voiceauth.domain.model.Voiceprint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VoiceEnrollmentViewModel @Inject constructor(
    private val repository: VoiceProfileRepository,
    private val engine: SpeakerVerificationEngine,
    private val logger: Logger
) : ViewModel() {

    private val _enrollmentState = MutableStateFlow<EnrollmentState>(EnrollmentState.Idle)
    val enrollmentState: StateFlow<EnrollmentState> = _enrollmentState.asStateFlow()

    private val collectedEmbeddings = mutableListOf<FloatArray>()
    private val requiredSamples = 3

    init {
        checkExistingProfile()
    }

    private fun checkExistingProfile() {
        viewModelScope.launch {
            if (repository.hasEnrolledVoiceprint()) {
                _enrollmentState.value = EnrollmentState.AlreadyEnrolled
            }
        }
    }

    fun startEnrollment() {
        collectedEmbeddings.clear()
        _enrollmentState.value = EnrollmentState.Recording(1, requiredSamples)
        logger.i("VoiceEnrollment", "Started new enrollment flow. Expecting \$requiredSamples samples.")
    }

    fun processVoiceSample(audioBuffer: ByteBuffer) {
        if (_enrollmentState.value !is EnrollmentState.Recording) return

        viewModelScope.launch {
            _enrollmentState.value = EnrollmentState.Processing
            try {
                // Extract unique features
                val embedding = engine.extractEmbedding(audioBuffer)
                collectedEmbeddings.add(embedding)

                if (collectedEmbeddings.size >= requiredSamples) {
                    finalizeEnrollment()
                } else {
                    _enrollmentState.value = EnrollmentState.Recording(collectedEmbeddings.size + 1, requiredSamples)
                }
            } catch (e: Exception) {
                logger.e("VoiceEnrollment", "Error processing sample", e)
                _enrollmentState.value = EnrollmentState.Error("Extraction failed.")
            }
        }
    }

    private suspend fun finalizeEnrollment() {
        logger.i("VoiceEnrollment", "Averaging \${collectedEmbeddings.size} samples for robust voiceprint.")
        
        // Simple averaging of embeddings (d-vectors) for baseline robustness
        val finalSize = collectedEmbeddings.first().size
        val averagedEmbedding = FloatArray(finalSize)
        
        for (i in 0 until finalSize) {
            var sum = 0f
            for (emb in collectedEmbeddings) {
                sum += emb[i]
            }
            averagedEmbedding[i] = sum / collectedEmbeddings.size
        }

        val newVoiceprint = Voiceprint(
            id = UUID.randomUUID().toString(),
            ownerName = "Primary Owner", 
            embedding = averagedEmbedding,
            createdAt = System.currentTimeMillis()
        )

        repository.saveVoiceprint(newVoiceprint)
        logger.i("VoiceEnrollment", "Enrollment successful. Voiceprint saved securely.")
        _enrollmentState.value = EnrollmentState.Success
    }

    fun clearVoiceprint() {
        viewModelScope.launch {
            repository.getPrimaryVoiceprint()?.let {
                repository.deleteVoiceprint(it.id)
                _enrollmentState.value = EnrollmentState.Idle
            }
        }
    }
}

sealed class EnrollmentState {
    object Idle : EnrollmentState()
    object AlreadyEnrolled : EnrollmentState()
    data class Recording(val currentSample: Int, val totalSamples: Int) : EnrollmentState()
    object Processing : EnrollmentState()
    object Success : EnrollmentState()
    data class Error(val message: String) : EnrollmentState()
}
