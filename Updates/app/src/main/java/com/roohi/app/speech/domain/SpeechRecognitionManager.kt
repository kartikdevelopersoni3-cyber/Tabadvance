package com.roohi.app.speech.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.speech.domain.models.SpeechResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechRecognitionManager @Inject constructor(
    private val speechProvider: SpeechRecognitionProvider,
    private val speechRepository: SpeechRecognitionRepository,
    private val logger: Logger
) {

    private val _speechState = MutableStateFlow<SpeechResult?>(null)
    val speechState: Flow<SpeechResult?> = _speechState.asStateFlow()

    private var recognitionJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun startRecognition() {
        if (recognitionJob?.isActive == true) {
            logger.w("SpeechManager", "Recognition already running.")
            return
        }

        logger.i("SpeechManager", "Starting speech recognition.")
        recognitionJob = scope.launch {
            speechProvider.startListening()
                .catch { e ->
                    logger.e("SpeechManager", "Recognition stream error", e)
                    _speechState.value = SpeechResult("", false, 0f, "Stream Error")
                }
                .onCompletion {
                    logger.i("SpeechManager", "Recognition stream completed.")
                }
                .collect { result ->
                    _speechState.value = result
                    if (!result.isPartial && result.error == null) {
                        speechRepository.saveLatestTranscript(result.transcript, result.confidenceScore)
                        logger.i("SpeechManager", "Final result saved: \${result.transcript}")
                    }
                }
        }
    }

    fun stopRecognition() {
        logger.i("SpeechManager", "Stopping speech recognition.")
        speechProvider.stopListening()
        recognitionJob?.cancel()
        recognitionJob = null
    }

    fun restartRecognition() {
        stopRecognition()
        startRecognition()
    }
}
