package com.roohi.app.speech.data.provider

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.roohi.app.core.logging.Logger
import com.roohi.app.speech.domain.SpeechRecognitionProvider
import com.roohi.app.speech.domain.models.SpeechResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AndroidSpeechProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) : SpeechRecognitionProvider {

    private var speechRecognizer: SpeechRecognizer? = null

    override fun startListening(): Flow<SpeechResult> = callbackFlow {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            logger.e("AndroidSpeechProvider", "Speech recognition is not available on this device.")
            trySend(SpeechResult("", false, 0.0f, "Service Unavailable"))
            close()
            return@callbackFlow
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN") // Hinglish priority
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        launchOnMainThread {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    logger.i("AndroidSpeechProvider", "Ready for speech.")
                }

                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {
                    logger.i("AndroidSpeechProvider", "End of speech.")
                }

                override fun onError(error: Int) {
                    val errorMessage = getErrorText(error)
                    logger.e("AndroidSpeechProvider", "Speech recognition error: \$errorMessage")
                    trySend(SpeechResult("", false, 0.0f, errorMessage))
                    close()
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val scores = results?.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
                    if (!matches.isNullOrEmpty()) {
                        val text = matches[0]
                        val confidence = scores?.getOrNull(0) ?: 0.0f
                        trySend(SpeechResult(text, false, confidence))
                    }
                    close()
                }

                override fun onPartialResults(partialResults: Bundle?) {
                    val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        trySend(SpeechResult(matches[0], true))
                    }
                }

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
            speechRecognizer?.startListening(intent)
        }

        awaitClose {
            launchOnMainThread {
                speechRecognizer?.cancel()
                speechRecognizer?.setRecognitionListener(null)
                speechRecognizer?.destroy()
                speechRecognizer = null
            }
        }
    }

    override fun stopListening() {
        launchOnMainThread {
            speechRecognizer?.stopListening()
        }
    }

    override fun destroy() {
        launchOnMainThread {
            speechRecognizer?.destroy()
            speechRecognizer = null
        }
    }

    private fun launchOnMainThread(block: () -> Unit) {
        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        handler.post(block)
    }

    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "Error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
    }
}
