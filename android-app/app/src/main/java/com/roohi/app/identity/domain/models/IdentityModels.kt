package com.roohi.app.identity.domain.models

data class OwnerProfile(
    val id: String,
    val ownerName: String,
    val nickname: String,
    val language: String,
    val region: String,
    val emergencyPreferences: String,
    val accessibilityPreferences: String,
    val tabletPreferences: String,
    val updatedAt: Long
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val relationship: String,
    val isPrimary: Boolean,
    val isMedical: Boolean,
    val metadata: String
)

data class DeviceMetadata(
    val id: String,
    val installationId: String,
    val deviceId: String,
    val tabletMetadata: String,
    val androidVersion: String,
    val buildVersion: String,
    val registrationTimestamp: Long,
    val lastValidationTimestamp: Long
)

enum class SetupState {
    NOT_STARTED,
    OWNER_PROFILE_DONE,
    VOICE_ENROLLMENT_DONE,
    PERMISSIONS_DONE,
    EMERGENCY_CONTACTS_DONE,
    API_SETUP_DONE,
    COMPLETED
}

data class PermissionHealth(
    val microphoneGranted: Boolean,
    val notificationsGranted: Boolean,
    val overlayGranted: Boolean,
    val batteryOptimizationIgnored: Boolean,
    val allCriticalGranted: Boolean
)

data class SystemIdentityDiagnostics(
    val isOwnerRegistered: Boolean,
    val voiceProfileStatus: String,
    val setupState: SetupState,
    val emergencyContactCount: Int,
    val apiConfigured: Boolean,
    val deviceRegistered: Boolean,
    val permissionHealth: PermissionHealth,
    val securityStatus: String,
    val systemReadiness: Boolean
)
