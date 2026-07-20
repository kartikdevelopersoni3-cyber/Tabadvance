package com.roohi.app.speech.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import com.roohi.app.core.logging.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    init {
        logger.i("TextToSpeechManager", "Initializing Android TTS...")
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val locale = Locale("hi", "IN")
            val result = tts?.setLanguage(locale)
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                logger.w("TextToSpeechManager", "Hindi (IN) not supported, falling back to English (IN).")
                val fallbackResult = tts?.setLanguage(Locale("en", "IN"))
                
                if (fallbackResult == TextToSpeech.LANG_MISSING_DATA || fallbackResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                   logger.w("TextToSpeechManager", "English (IN) not supported either, using default.")
                   tts?.setLanguage(Locale.getDefault())
                }
            }
            logger.i("TextToSpeechManager", "TTS Initialization Successful.")
            _isReady.value = true
        } else {
            logger.e("TextToSpeechManager", "TTS Initialization Failed with status: $status")
            _isReady.value = false
        }
    }

    fun speak(text: String, interrupt: Boolean = true) {
        if (_isReady.value) {
            val queueMode = if (interrupt) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
            tts?.speak(text, queueMode, null, "ROOHI_TTS_UTTERANCE")
            logger.i("TextToSpeechManager", "Speaking: $text")
        } else {
            logger.w("TextToSpeechManager", "TTS called but not ready. Text: $text")
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        _isReady.value = false
        logger.i("TextToSpeechManager", "TTS Shutdown.")
    }
}
