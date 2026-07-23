package com.roohi.app.reasoning.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.reasoning.domain.models.ReasoningSession
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionReasoner @Inject constructor(
    private val goalManager: GoalManager,
    private val logger: Logger
) {
    private var activeSession: ReasoningSession? = null

    suspend fun restoreOrStartSession(): ReasoningSession {
        if (activeSession != null) {
            val valid = (System.currentTimeMillis() - activeSession!!.lastActiveTime) < 3600000 // 1 hour validity
            if (valid) {
                 logger.i("SessionReasoner", "Restoring active session: ${activeSession?.sessionId}")
                 return activeSession!!
            }
        }
        
        logger.i("SessionReasoner", "Starting new reasoning session.")
        val activeGoals = goalManager.getActiveGoals()
        val session = ReasoningSession(UUID.randomUUID().toString(), activeGoals, System.currentTimeMillis())
        activeSession = session
        return session
    }
    
    fun pingSession() {
        activeSession = activeSession?.copy(lastActiveTime = System.currentTimeMillis())
    }
}
