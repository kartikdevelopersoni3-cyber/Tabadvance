package com.roohi.app.identity.domain

import android.os.Build
import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.models.DeviceMetadata
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRegistrationManager @Inject constructor(
    private val repository: SystemIdentityRepository,
    private val logger: Logger
) {
    suspend fun registerDeviceIfNeeded() {
        val existing = repository.getDeviceMetadata()
        if (existing == null) {
            val metadata = DeviceMetadata(
                id = UUID.randomUUID().toString(),
                installationId = UUID.randomUUID().toString(),
                deviceId = Build.MODEL ?: "Unknown",
                tabletMetadata = "tablet_default",
                androidVersion = Build.VERSION.RELEASE ?: "Unknown",
                buildVersion = Build.DISPLAY ?: "Unknown",
                registrationTimestamp = System.currentTimeMillis(),
                lastValidationTimestamp = System.currentTimeMillis()
            )
            repository.saveDeviceMetadata(metadata)
            logger.i("DeviceRegistrationManager", "Device newly registered: \${metadata.installationId}")
        } else {
            logger.i("DeviceRegistrationManager", "Device already registered.")
        }
    }
    
    suspend fun getDeviceData(): DeviceMetadata? {
        return repository.getDeviceMetadata()
    }
}
