package com.roohi.app.background.monitor

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Singleton
class SystemWatchdog @Inject constructor(
    private val logger: Logger
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var isRunning = false

    fun start() {
        if (isRunning) return
        isRunning = true
        scope.launch {
            while (isRunning) {
                delay(30000L)
                logger.i("SystemWatchdog", "Watchdog Ping. Core System Alive.")
            }
        }
    }

    fun stop() {
        isRunning = false
    }
}
