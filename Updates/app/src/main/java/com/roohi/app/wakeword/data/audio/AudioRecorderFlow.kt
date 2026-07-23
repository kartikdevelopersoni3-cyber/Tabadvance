package com.roohi.app.wakeword.data.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.roohi.app.core.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AudioRecorderFlow @Inject constructor(
    private val logger: Logger,
    private val audioFocusManager: AudioFocusManager
) {

    companion object {
        private val SAMPLE_RATES = intArrayOf(16000, 22050, 44100, 48000)
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }

    @Volatile
    private var audioRecord: AudioRecord? = null
    
    private val recordLock = Any()

    @Volatile
    var isRecording = false
        private set

    @SuppressLint("MissingPermission") // Handled by permission rule
    fun startStreaming(): Flow<ShortArray> = flow {
        synchronized(recordLock) {
            if (isRecording) {
                logger.w("AudioRecorderFlow", "Already recording. Return empty flow.")
                return@flow
            }

            if (!audioFocusManager.requestAudioFocus()) {
                logger.e("AudioRecorderFlow", "Failed to get audio focus. Cannot start listening.")
                return@flow
            }

            var activeSampleRate = -1
            var bufferSize = -1

            for (rate in SAMPLE_RATES) {
                val size = AudioRecord.getMinBufferSize(rate, CHANNEL_CONFIG, AUDIO_FORMAT)
                if (size != AudioRecord.ERROR && size != AudioRecord.ERROR_BAD_VALUE) {
                    activeSampleRate = rate
                    bufferSize = size
                    break
                }
            }

            if (bufferSize <= 0) {
                logger.e("AudioRecorderFlow", "No valid audio record configuration found.")
                audioFocusManager.abandonAudioFocus()
                return@flow
            }

            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                activeSampleRate,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                bufferSize
            )

            if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
                logger.e("AudioRecorderFlow", "AudioRecord initialization failed.")
                audioFocusManager.abandonAudioFocus()
                return@flow
            }

            logger.i("AudioRecorderFlow", "Starting AudioRecord stream with rate \$activeSampleRate...")
            try {
                audioRecord?.startRecording()
                isRecording = true
            } catch (e: Exception) {
                logger.e("AudioRecorderFlow", "Exception during AudioRecord start", e)
                return@flow
            }
        } // end synchronized

        try {
            val bufferSize = AudioRecord.getMinBufferSize(16000, CHANNEL_CONFIG, AUDIO_FORMAT) // just used as reference or we can just fetch
            val safeBufferSize = if (bufferSize > 0) bufferSize else 2048
            val audioBuffer = ShortArray(safeBufferSize / 2) // ShortArray is half the byte size

            while (isRecording) {
                val currentRecord = audioRecord
                if (currentRecord == null) break
                val readResult = currentRecord.read(audioBuffer, 0, audioBuffer.size)
                if (readResult > 0) {
                    emit(audioBuffer.copyOf()) // emit to down-stream flow
                } else if (readResult < 0) {
                    logger.w("AudioRecorderFlow", "Audio stream read error code: \$readResult")
                    break
                }
            }
        } catch (e: Exception) {
            logger.e("AudioRecorderFlow", "Exception during recording stream", e)
        } finally {
            logger.i("AudioRecorderFlow", "Audio stream flow terminated.")
            stop()
        }
    }.flowOn(Dispatchers.IO) // Read heavy IO operations asynchronously

    fun stop() {
        synchronized(recordLock) {
            if (!isRecording) return
            isRecording = false
            try {
                audioRecord?.stop()
            } catch (e: Exception) {
                logger.e("AudioRecorderFlow", "Error stopping audioRecord", e)
            }
            audioRecord?.release()
            audioRecord = null
            audioFocusManager.abandonAudioFocus()
        }
    }
}
