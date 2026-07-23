package com.roohi.app.background.monitor

import android.content.Context
import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.PermissionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoundationHealthMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serviceHealthMonitor: ServiceHealthMonitor,
    private val permissionManager: PermissionManager,
    private val reasoningHealthMonitor: com.roohi.app.reasoning.domain.ReasoningHealthMonitor,
    private val personalityHealthMonitor: com.roohi.app.personality.monitor.PersonalityHealthMonitor,
    private val ownerHealthMonitor: com.roohi.app.owner.monitor.OwnerHealthMonitor,
    private val executionHealthMonitor: com.roohi.app.execution.monitor.ExecutionHealthMonitor,
    private val deviceHealthMonitor: com.roohi.app.device.monitor.DeviceHealthMonitor,
    private val agentHealthMonitor: com.roohi.app.coordination.monitor.AgentHealthMonitor,
    private val visionHealthMonitor: com.roohi.app.vision.monitor.VisionHealthMonitor,
    private val automationHealthMonitor: com.roohi.app.automation.monitor.AutomationHealthMonitor,
    private val learningHealthMonitor: com.roohi.app.learning.monitor.LearningHealthMonitor,
    private val knowledgeHealthMonitor: com.roohi.app.knowledge.monitor.KnowledgeHealthMonitor,
    private val proactiveHealthMonitor: com.roohi.app.proactive.monitor.ProactiveHealthMonitor,
    private val workspaceHealthMonitor: com.roohi.app.workspace.monitor.WorkspaceHealthMonitor,
    private val systemWatchdog: SystemWatchdog,
    private val dependencyValidator: DependencyValidator,
    private val runtimeValidator: RuntimeValidator,
    private val securityValidator: SecurityValidator,
    private val failureTracker: FailureTracker,
    private val recoveryMonitor: RecoveryMonitor,
    private val safeMode: SafeMode,
    private val gracefulDegradation: GracefulDegradation,
    private val logger: Logger
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var monitorJob: Job? = null

    fun startMonitoring() {
        if (monitorJob?.isActive == true) return
        
        systemWatchdog.start()
        dependencyValidator.validateDependencies()
        runtimeValidator.validateRuntimeIntegrity()
        securityValidator.validateSecurityPolicies()

        monitorJob = scope.launch {
            while (true) {
                delay(60_000L) // Default interval
                validateRuntimeAndRecover()
            }
        }
        logger.i("FoundationHealthMonitor", "Foundation Health Monitor active.")
    }
    
    private fun validateRuntimeAndRecover() {
        val perms = permissionManager.checkPermissionHealth()
        
        if (!perms.allCriticalGranted) {
            logger.e("FoundationHealthMonitor", "CRITICAL DETECTED: Mic/Notify missing! Entering Safe Mode.")
            enableSafeMode()
            return
        }

        if (!reasoningHealthMonitor.checkHealth()) {
            logger.w("FoundationHealthMonitor", "Reasoning Engine Unhealthy. Applying Fix.")
            reasoningHealthMonitor.reportRecovery()
            failureTracker.recordFailure("ReasoningManager", "Health check failed")
            recoveryMonitor.triggerRecovery("ReasoningManager")
        }
        
        if (!agentHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Agent Network Unhealthy. Recovering dead agents.")
            failureTracker.recordFailure("AgentCoordinator", "Multi-Agent network deadlocks detected")
        }
        
        if (!personalityHealthMonitor.checkHealth()) {
            logger.w("FoundationHealthMonitor", "Personality Drift Detected via Foundation Monitor.")
            failureTracker.recordFailure("PersonalityManager", "Health check failed")
        }
        
        if (!ownerHealthMonitor.validate()) {
            logger.w("FoundationHealthMonitor", "Owner Profile Corruption Detected.")
            failureTracker.recordFailure("OwnerConfigurationManager", "Validation failed")
        }
        
        if (!executionHealthMonitor.isHealthy()) {
             logger.w("FoundationHealthMonitor", "Execution Queue Unhealthy. Checking deadlocks.")
             failureTracker.recordFailure("TaskManager", "Deadlock check failed")
        }
        
        if (!deviceHealthMonitor.isHealthy()) {
             logger.w("FoundationHealthMonitor", "Device Abstraction Subsystem Unhealthy.")
             failureTracker.recordFailure("DeviceControlManager", "Device state validation failed")
        }
        
        if (!visionHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Vision Subsystem Unhealthy.")
            failureTracker.recordFailure("VisionManager", "Vision sensors or ML failed")
        }
        
        if (!automationHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Automation Subsystem Unhealthy.")
            failureTracker.recordFailure("AutomationManager", "Automation workflows stalled")
        }
        
        if (!learningHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Learning Subsystem Unhealthy.")
            failureTracker.recordFailure("LearningManager", "Adaptation metrics corrupted")
        }
        
        if (!knowledgeHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Knowledge Subsystem Unhealthy.")
            failureTracker.recordFailure("KnowledgeManager", "Knowledge graph issues detected")
        }
        
        if (!proactiveHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Proactive Subsystem Unhealthy.")
            failureTracker.recordFailure("ProactiveManager", "Recommendation thresholds corrupted")
        }
        
        if (!workspaceHealthMonitor.isHealthy()) {
            logger.w("FoundationHealthMonitor", "Workspace OS Subsystem Unhealthy.")
            failureTracker.recordFailure("WorkspaceManager", "Workspace OS components failed")
        }
        
        if (!serviceHealthMonitor.isServiceHealthy()) {
            logger.w("FoundationHealthMonitor", "Service Unhealthy. Attempting Self-Recovery.")
            failureTracker.recordFailure("RoohiBackgroundService", "Service check failed")
            serviceHealthMonitor.reportSelfRecovery()
            recoveryMonitor.triggerRecovery("RoohiBackgroundService")
            
            if (failureTracker.getFailureCount("RoohiBackgroundService") > 3) {
                logger.e("FoundationHealthMonitor", "RECOVERY LOOP DETECTED. Graceful Degradation Activated.")
                enableGracefulDegradation()
            }
        } else {
            failureTracker.clear("RoohiBackgroundService")
            logger.d("FoundationHealthMonitor", "Foundation Runtime Stable.")
        }
    }
    
    private fun enableSafeMode() {
        safeMode.activateSafeMode("Critical Permissions Missing")
        logger.w("FoundationHealthMonitor", "SAFE MODE ACTIVE. Halting all background listeners.")
        // Gracefully kill services waiting for UI restart
    }
    
    private fun enableGracefulDegradation() {
       gracefulDegradation.applyDegradation()
       logger.w("FoundationHealthMonitor", "GRACEFUL DEGRADATION: Skipping network tasks, enforcing pure offline limits until restart.")
    }

    fun trackFailure(component: String) {
        failureTracker.recordFailure(component, "External tracked failure")
    }

    fun stopMonitoring() {
        monitorJob?.cancel()
        systemWatchdog.stop()
        logger.i("FoundationHealthMonitor", "Foundation Health Monitor stopped.")
    }
}
