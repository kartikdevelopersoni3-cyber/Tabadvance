package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.execution.domain.ExecutionPlanner
import com.roohi.app.reasoning.domain.models.ReasoningContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Singleton
class DecisionEngine @Inject constructor(
    private val executionPlanner: ExecutionPlanner,
    private val agentCoordinator: com.roohi.app.coordination.domain.AgentCoordinator,
    private val logger: Logger
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun evaluateOptions(context: ReasoningContext, options: List<String>): String {
        logger.i("DecisionEngine", "Evaluating ${options.size} options based on context")
        val chosenAction = options.firstOrNull() ?: "UNKNOWN_ACTION"
        
        scope.launch {
             // Multi-agent orchestration fallback replacing hard-coded executions dynamically:
             agentCoordinator.coordinateTask(chosenAction)
             
             // Legacy fallback for tests
             executionPlanner.planAndEnqueueTask(chosenAction, isEmergency = context.intent == "EMERGENCY")
        }
        
        return chosenAction
    }
}
