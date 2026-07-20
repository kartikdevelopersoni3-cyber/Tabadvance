package com.roohi.app.wakeword.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.voiceauth.domain.VoiceAuthManager
import com.roohi.app.speech.domain.SpeechRecognitionManager
import com.roohi.app.wakeword.data.audio.AudioRecorderFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WakeWordManager @Inject constructor(
    private val audioRecorder: AudioRecorderFlow,
    private val engine: WakeWordEngine,
    private val voiceAuthManager: VoiceAuthManager,
    private val speechRecognitionManager: SpeechRecognitionManager,
    private val logger: Logger
) {
    private val managerScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var listeningJob: Job? = null

    suspend fun startListening() {
        if (listeningJob?.isActive == true) return
        
        logger.i("WakeWordManager", "Initializing engine...")
        engine.initialize()

        logger.i("WakeWordManager", "Starting audio intake stream...")
        val audioStream = audioRecorder.startStreaming()

        listeningJob = managerScope.launch {
            engine.startListening(audioStream)
                .catch { e -> logger.e("WakeWordManager", "Engine crash", e) }
                .onCompletion { logger.w("WakeWordManager", "Engine flow halted.") }
                .collect { capturedAudioBuffer ->
                    // Flow chain triggered! We detected "Roohi!"
                    logger.i("WakeWordManager", "WAKE WORD TRIGGERED. Holding stream, initiating Voice Auth.")
                    
                    // We must verify the person who just spoke the wake word is the Owner
                    val isOwner = voiceAuthManager.verifyActiveAudio(capturedAudioBuffer)

                    if (isOwner) {
                        logger.w("WakeWordManager", "OWNER VERIFIED. Executing Assistant Activation routine...")
                        // Ensure we pause the WakeWord engine briefly or let Speech stream overlay safely.
                        // We must suspend audio focus from the wakeword mic flow so AndroidSpeechProvider can take over.
                        audioRecorder.stop()
                        speechRecognitionManager.startRecognition()
                    } else {
                        logger.w("WakeWordManager", "REJECTED. Voice signature did not match Owner. Returning to listening mode.")
                    }
                }
        }
    }

    fun stop() {
        logger.i("WakeWordManager", "Shutting down WakeWordManager.")
        listeningJob?.cancel()
        engine.stopListening()
        audioRecorder.stop()
    }
}
