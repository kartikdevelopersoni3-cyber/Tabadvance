package com.roohi.app.identity.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.identity.domain.models.EmergencyContact
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class EmergencyContactManager @Inject constructor(
    private val repository: SystemIdentityRepository,
    private val logger: Logger
) {
    suspend fun addContact(name: String, phoneNumber: String, relationship: String, isPrimary: Boolean, isMedical: Boolean) {
        val contact = EmergencyContact(
            id = UUID.randomUUID().toString(),
            name = name,
            phoneNumber = phoneNumber,
            relationship = relationship,
            isPrimary = isPrimary,
            isMedical = isMedical,
            metadata = ""
        )
        repository.saveEmergencyContact(contact)
        logger.i("EmergencyContactManager", "Emergency contact added: \$name")
    }

    suspend fun getAllContacts(): List<EmergencyContact> {
        return repository.getAllEmergencyContacts()
    }

    suspend fun removeContact(id: String) {
        repository.deleteEmergencyContact(id)
        logger.i("EmergencyContactManager", "Emergency contact removed: \$id")
    }
}
