package com.roohi.app.background.monitor

import com.roohi.app.core.logging.Logger
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FailureTracker @Inject constructor(
    private val logger: Logger
) {
    private val failures = ConcurrentHashMap<String, Int>()

    fun recordFailure(module: String, reason: String) {
        val count = failures.getOrDefault(module, 0) + 1
        failures[module] = count
        logger.e("FailureTracker", "Recorded failure in $module ($count): $reason")
    }

    fun getFailureCount(module: String): Int = failures.getOrDefault(module, 0)
    
    fun clear(module: String) {
        failures[module] = 0
    }
}
