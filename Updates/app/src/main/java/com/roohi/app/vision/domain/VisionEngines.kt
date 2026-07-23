package com.roohi.app.vision.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.vision.data.VisionDao
import com.roohi.app.vision.data.VisualMemoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisualMemoryManager @Inject constructor(
    private val visionDao: VisionDao,
    private val logger: Logger
) {
    suspend fun storeVisualContext(contextSummary: String, ocrResult: String, screenState: String, isEmergency: Boolean) {
        logger.d("VisualMemoryManager", "Storing visual memory.")
        val entity = VisualMemoryEntity(
            contextSummary = contextSummary,
            ocrContent = ocrResult,
            screenState = screenState,
            isEmergency = isEmergency
        )
        visionDao.insertVisualMemory(entity)
    }

    suspend fun retrieveRecentMemory(): List<VisualMemoryEntity> {
        return visionDao.getRecentVisualMemories()
    }
}

@Singleton
class ImageAnalysisEngine @Inject constructor(
    private val ocrManager: OCRManager,
    private val logger: Logger
) {
    fun analyze(imagePath: String): String {
        logger.i("ImageAnalysisEngine", "Performing ML analysis on image.")
        val text = ocrManager.processImage(imagePath)
        return "Analyzed Content with Text: $text"
    }
}

@Singleton
class VisualContextEngine @Inject constructor(
    private val imageAnalysisEngine: ImageAnalysisEngine,
    private val screenAnalysisManager: ScreenAnalysisManager,
    private val logger: Logger
) {
    fun buildContext(imagePath: String): String {
        val analysis = imageAnalysisEngine.analyze(imagePath)
        val screenState = screenAnalysisManager.analyzeScreenState()
        return "VisualContext -> Screen: $screenState | Analysis: $analysis"
    }
}

@Singleton
class VisionManager @Inject constructor(
    private val cameraManager: CameraManager,
    private val visualContextEngine: VisualContextEngine,
    private val visualMemoryManager: VisualMemoryManager,
    private val logger: Logger
) {
    suspend fun processVisualInput() {
        logger.i("VisionManager", "Processing visual input stream.")
        val imagePath = cameraManager.captureImage()
        val context = visualContextEngine.buildContext(imagePath)
        visualMemoryManager.storeVisualContext(contextSummary = context, ocrResult = "raw text", screenState = "Foreground", isEmergency = false)
        logger.i("VisionManager", "Visual processing complete.")
    }
}
