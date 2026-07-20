package com.roohi.app.core.ml

import com.roohi.app.core.logging.Logger
import org.tensorflow.lite.Interpreter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterpreterProvider @Inject constructor(
    private val modelLoader: ModelLoader,
    private val logger: Logger
) {

    fun getInterpreter(modelName: String): InterpreterWrapper {
        val modelBuffer = modelLoader.loadMappedAsset(modelName)
        if (modelBuffer != null) {
            return try {
                val interpreter = Interpreter(modelBuffer, Interpreter.Options().apply { setNumThreads(2) })
                logger.i("InterpreterProvider", "Successfully loaded model: \$modelName")
                InterpreterWrapper.Active(interpreter)
            } catch (e: Exception) {
                logger.e("InterpreterProvider", "Error initializing Interpreter for \$modelName", e)
                InterpreterWrapper.Stub
            }
        } else {
            logger.w("InterpreterProvider", "Asset file \$modelName not found! Proceeding in ML STUB mode.")
            return InterpreterWrapper.Stub
        }
    }

    fun verifyAssets(vararg assetNames: String): Map<String, Boolean> {
        val statusMap = mutableMapOf<String, Boolean>()
        for (asset in assetNames) {
            statusMap[asset] = modelLoader.doesAssetExist(asset)
        }
        return statusMap
    }
}

sealed class InterpreterWrapper {
    data class Active(val interpreter: Interpreter) : InterpreterWrapper()
    object Stub : InterpreterWrapper()
}
