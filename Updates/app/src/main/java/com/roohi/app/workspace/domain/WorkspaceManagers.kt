package com.roohi.app.workspace.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.workspace.data.WorkspaceDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(private val logger: Logger) {
    fun initSession(type: String) { logger.i("SessionManager", "Initializing $type session.") }
}

@Singleton
class ProjectManager @Inject constructor(private val logger: Logger) {
    fun setProjectContext(project: String) { logger.d("ProjectManager", "Setting active project: $project") }
}

@Singleton
class SmartDashboardManager @Inject constructor(private val logger: Logger) {
    fun refreshDashboard() { logger.d("SmartDashboardManager", "Refreshing AI metrics dashboard UI states.") }
}

@Singleton
class WorkspaceManager @Inject constructor(
    private val workspaceDao: WorkspaceDao,
    private val sessionManager: SessionManager,
    private val projectManager: ProjectManager,
    private val contextWorkspaceEngine: ContextWorkspaceEngine,
    private val dashboardManager: SmartDashboardManager,
    private val focusWorkspaceEngine: FocusWorkspaceEngine,
    private val multiTaskCoordinator: MultiTaskCoordinator,
    private val notificationIntelligenceEngine: NotificationIntelligenceEngine,
    private val logger: Logger
) {
    suspend fun switchToWorkspace(workspaceName: String) {
        logger.i("WorkspaceManager", "Switching OS view to workspace: $workspaceName")
        val context = contextWorkspaceEngine.evaluateContext()
        sessionManager.initSession(context)
        focusWorkspaceEngine.engageFocus("DEEP_WORK")
        multiTaskCoordinator.coordinateSubsystems()
        notificationIntelligenceEngine.filterNotifications(context)
        dashboardManager.refreshDashboard()
    }
}
