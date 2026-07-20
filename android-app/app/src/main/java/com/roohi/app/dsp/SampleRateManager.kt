package com.roohi.app.dsp

import android.media.AudioFormat
import android.media.AudioRecord
import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleRateManager @Inject constructor(
    private val logger: Logger
) {

    companion object {
        private val SUPPORTED_RATES = intArrayOf(16000, 22050, 44100, 48000)
    }

    fun getOptimalSampleRate(): Int {
        for (rate in SUPPORTED_RATES) {
            val bufferSize = AudioRecord.getMinBufferSize(
                rate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            if (bufferSize != AudioRecord.ERROR && bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                logger.i("SampleRateManager", "Optimal Sample Rate determined: \$rate")
                return rate
            }
        }
        logger.e("SampleRateManager", "No supported sample rate found on this hardware!")
        return 16000 // Fallback minimum
    }
}
