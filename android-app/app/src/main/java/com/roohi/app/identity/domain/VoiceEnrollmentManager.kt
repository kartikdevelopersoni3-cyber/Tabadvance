package com.roohi.app.identity.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.voiceauth.domain.VoiceAuthManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceEnrollmentManager @Inject constructor(
    private val voiceAuthManager: VoiceAuthManager,
    private val logger: Logger
) {
    suspend fun getEnrollmentStatus(): String {
        val enrolled = voiceAuthManager.isOwnerEnrolled()
        return if (enrolled) "ENROLLED" else "NOT_ENROLLED"
    }

    suspend fun clearEnrollment() {
        voiceAuthManager.clearAllVoiceprints()
        logger.i("VoiceEnrollmentManager", "Cleared voice enrollments via wizard.")
    }
}
