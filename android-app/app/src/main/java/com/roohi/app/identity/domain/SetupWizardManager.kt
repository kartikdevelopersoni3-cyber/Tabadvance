package com.roohi.app.identity.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.models.SetupState
import com.roohi.app.identity.domain.models.SystemIdentityDiagnostics
import com.roohi.app.identity.domain.security.SecureCredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetupWizardManager @Inject constructor(
    private val repository: SystemIdentityRepository,
    private val ownerProfileManager: OwnerProfileManager,
    private val permissionManager: PermissionManager,
    private val voiceEnrollmentManager: VoiceEnrollmentManager,
    private val emergencyContactManager: EmergencyContactManager,
    private val deviceRegistrationManager: DeviceRegistrationManager,
    private val secureCredentialManager: SecureCredentialManager,
    private val logger: Logger
) {
    private val _diagnostics = MutableStateFlow(
        SystemIdentityDiagnostics(
            isOwnerRegistered = false,
            voiceProfileStatus = "UNKNOWN",
            setupState = SetupState.NOT_STARTED,
            emergencyContactCount = 0,
            apiConfigured = false,
            deviceRegistered = false,
            permissionHealth = permissionManager.checkPermissionHealth(),
            securityStatus = "LOCKED",
            systemReadiness = false
        )
    )
    val diagnostics: StateFlow<SystemIdentityDiagnostics> = _diagnostics.asStateFlow()

    suspend fun refreshDiagnostics() {
        val owner = ownerProfileManager.getProfile()
        val setupState = repository.getSetupState()
        val contacts = emergencyContactManager.getAllContacts()
        val voiceStatus = voiceEnrollmentManager.getEnrollmentStatus()
        val device = deviceRegistrationManager.getDeviceData()
        
        // Simple API check from secure storage mock
        val hasApiKey = secureCredentialManager.getCredential("gemini_api_key") != null

        val perms = permissionManager.checkPermissionHealth()
        
        val isReady = owner != null && voiceStatus == "ENROLLED" && perms.allCriticalGranted && device != null

        _diagnostics.value = SystemIdentityDiagnostics(
            isOwnerRegistered = owner != null,
            voiceProfileStatus = voiceStatus,
            setupState = setupState,
            emergencyContactCount = contacts.size,
            apiConfigured = hasApiKey,
            deviceRegistered = device != null,
            permissionHealth = perms,
            securityStatus = if (hasApiKey) "ENCRYPTED" else "PENDING_KEYS",
            systemReadiness = isReady
        )
        logger.i("SetupWizardManager", "Diagnostics refreshed. Readiness: \$isReady")
    }
    
    suspend fun advanceSetupState(newState: SetupState) {
        repository.updateSetupState(newState)
        refreshDiagnostics()
    }
    
    suspend fun mockCompleteSetupForTesting() {
        ownerProfileManager.createOrUpdateProfile("Roohi Owner", "Boss", "en-US", "US")
        deviceRegistrationManager.registerDeviceIfNeeded()
        advanceSetupState(SetupState.COMPLETED)
    }
}
