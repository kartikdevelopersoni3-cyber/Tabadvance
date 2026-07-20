package com.roohi.app.background.monitor

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeMode @Inject constructor(
    private val logger: Logger
) {
    var isSafeModeActive = false
        private set

    fun activateSafeMode(reason: String) {
        isSafeModeActive = true
        logger.e("SafeMode", "SAFE MODE ACTIVATED: $reason")
    }

    fun deactivateSafeMode() {
        isSafeModeActive = false
        logger.i("SafeMode", "SAFE MODE DEACTIVATED.")
    }
}
