package com.roohi.app.background.monitor

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GracefulDegradation @Inject constructor(
    private val logger: Logger
) {
    var isDegradationActive = false
        private set

    fun applyDegradation() {
        isDegradationActive = true
        logger.w("GracefulDegradation", "Graceful Degradation active. Offline limits enforced.")
    }
}
