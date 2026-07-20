package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskDecomposer @Inject constructor(
    private val logger: Logger
) {
    fun decomposeRequest(request: String): List<String> {
        logger.i("TaskDecomposer", "Decomposing task: $request")
        return listOf(
            "Analyze requirement",
            "Prepare resources",
            "Execute primary action",
            "Verify success"
        )
    }
}
