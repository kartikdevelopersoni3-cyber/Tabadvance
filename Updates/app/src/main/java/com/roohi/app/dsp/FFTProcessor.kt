package com.roohi.app.dsp

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Singleton
class FFTProcessor @Inject constructor(
    private val logger: Logger
) {

    /**
     * Computes the 1D Fast Fourier Transform (Cooley-Tukey Radix-2).
     * Automatically zero-pads input to the next power of 2.
     * Returns magnitude spectrum.
     */
    fun computeFFT(realInput: FloatArray): FloatArray {
        var n = realInput.size
        require(n > 0) { "Input size must be greater than 0" }

        // Find next power of 2
        var paddedSize = 1
        while (paddedSize < n) {
            paddedSize = paddedSize shl 1
        }

        val real = FloatArray(paddedSize) { 0.0f }
        System.arraycopy(realInput, 0, real, 0, n)
        n = paddedSize

        val imag = FloatArray(n) { 0.0f }

        // Bit-reversal permutation
        var j = 0
        for (i in 0 until n - 1) {
            if (i < j) {
                val tempR = real[i]
                real[i] = real[j]
                real[j] = tempR
            }
            var k = n shr 1
            while (k <= j) {
                j -= k
                k = k shr 1
            }
            j += k
        }

        // Cooley-Tukey decimation-in-time
        var size = 2
        while (size <= n) {
            val halfSize = size shr 1
            val angle = -2.0 * PI / size
            val wRealStep = cos(angle).toFloat()
            val wImagStep = sin(angle).toFloat()

            for (i in 0 until n step size) {
                var wReal = 1.0f
                var wImag = 0.0f

                for (k in 0 until halfSize) {
                    val evenIdx = i + k
                    val oddIdx = i + k + halfSize

                    val tReal = wReal * real[oddIdx] - wImag * imag[oddIdx]
                    val tImag = wReal * imag[oddIdx] + wImag * real[oddIdx]

                    real[oddIdx] = real[evenIdx] - tReal
                    imag[oddIdx] = imag[evenIdx] - tImag
                    real[evenIdx] = real[evenIdx] + tReal
                    imag[evenIdx] = imag[evenIdx] + tImag

                    // Update w
                    val nextWReal = wReal * wRealStep - wImag * wImagStep
                    val nextWImag = wReal * wImagStep + wImag * wRealStep
                    wReal = nextWReal
                    wImag = nextWImag
                }
            }
            size = size shl 1
        }

        // Calculate magnitude
        val magnitude = FloatArray(n / 2)
        for (i in 0 until n / 2) {
            magnitude[i] = sqrt(real[i] * real[i] + imag[i] * imag[i])
        }

        return magnitude
    }

    /**
     * Applies a Hanning window to the input array in place to reduce spectral leakage.
     */
    fun applyHanningWindow(data: FloatArray) {
        val n = data.size
        for (i in 0 until n) {
            val multiplier = (0.5 - 0.5 * cos(2.0 * PI * i / (n - 1))).toFloat()
            data[i] *= multiplier
        }
    }
}
