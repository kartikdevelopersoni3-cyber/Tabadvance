package com.roohi.app.identity.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoohiLoginManager @Inject constructor(
    private val logger: Logger
) {
    private var isAuthenticated = false

    fun authenticateWithPIN(pin: String): Boolean {
        // Simulated PIN validation mapped purely locally (can be tied to Keystore or SecureCredentialManager later)
        val valid = pin == "1234" // hardcoded mockup for tests
        if (valid) {
            isAuthenticated = true
            logger.i("RoohiLoginManager", "Authentication success with PIN.")
        }
        return valid
    }

    fun isCurrentlyAuthenticated(): Boolean = isAuthenticated
    
    fun logout() {
        isAuthenticated = false
        logger.i("RoohiLoginManager", "User logged out securely.")
    }
}
