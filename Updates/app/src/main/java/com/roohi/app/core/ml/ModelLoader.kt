package com.roohi.app.core.ml

import android.content.Context
import com.roohi.app.core.logging.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelLoader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger
) {

    /**
     * Attempts to load a TFLite model from the assets folder.
     * Returns a MappedByteBuffer if successful, or null if the file does not exist.
     */
    fun loadMappedAsset(assetFileName: String): MappedByteBuffer? {
        return try {
            val fileDescriptor = context.assets.openFd(assetFileName)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        } catch (e: IOException) {
            logger.e("ModelLoader", "Failed to load model \$assetFileName. Asset may be missing.", e)
            null
        }
    }

    fun doesAssetExist(assetFileName: String): Boolean {
        return try {
            context.assets.open(assetFileName).use { it.available() > 0 }
        } catch (e: IOException) {
            false
        }
    }
}
