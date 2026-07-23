package com.roohi.app.wakeword.data.engine

import com.roohi.app.core.logging.Logger
import com.roohi.app.core.ml.InterpreterProvider
import com.roohi.app.core.ml.InterpreterWrapper
import com.roohi.app.wakeword.domain.WakeWordEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

class ContinuousWakeWordEngine @Inject constructor(
    private val interpreterProvider: InterpreterProvider,
    private val logger: Logger
) : WakeWordEngine {

    companion object {
        const val WAKE_WORD_MODEL = "roohi_wakeword.tflite"
    }

    private var isListening = false
    private val bufferWindowMs = 2000 // We capture 2 seconds of audio after wake word for voice auth
    private var interpreterWrapper: InterpreterWrapper = InterpreterWrapper.Stub

    override suspend fun initialize() {
        logger.i("ContinuousWakeWordEngine", "Initializing WakeWord TFLite/DSP engine. Target keyword: [Roohi]")
        interpreterWrapper = interpreterProvider.getInterpreter(WAKE_WORD_MODEL)
    }

    override fun startListening(audioStream: Flow<ShortArray>): Flow<ByteBuffer> = flow {
        isListening = true
        logger.i("ContinuousWakeWordEngine", "Starting continuous audio stream analysis...")

        audioStream.collect { audioFrame ->
            if (!isListening) return@collect
            
            val wakeWordDetected = when (interpreterWrapper) {
                is InterpreterWrapper.Active -> {
                    // Logic for real inference
                    // (interpreterWrapper as InterpreterWrapper.Active).interpreter.run(...)
                    false 
                }
                is InterpreterWrapper.Stub -> {
                    // In stub mode, we won't trigger continuously. Just return false.
                    false
                }
            }

            if (wakeWordDetected) {
                logger.w("ContinuousWakeWordEngine", "WAKE WORD [ROOHI] DETECTED!")
                val responseBuffer = ByteBuffer.allocate(1024).order(ByteOrder.nativeOrder())
                emit(responseBuffer)
            }
        }
    }

    override fun stopListening() {
        logger.i("ContinuousWakeWordEngine", "Stopping wake word inference engine.")
        isListening = false
    }
}
