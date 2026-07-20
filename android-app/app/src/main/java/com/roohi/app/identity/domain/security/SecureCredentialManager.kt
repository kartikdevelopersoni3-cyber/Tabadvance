package com.roohi.app.identity.domain.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureCredentialManager @Inject constructor(
    private val context: Context,
    private val logger: Logger
) {
    private val PREF_NAME = "roohi_secure_prefs"
    
    // Lazy initialization to avoid MainThread blocking during injection
    private val securePrefs by lazy {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            logger.e("SecureCredentialManager", "Failed to init EncryptedSharedPreferences: \${e.message}")
            // Graceful fallback for devices that might fail Keystore init (e.g. some emulators or edge cases)
            context.getSharedPreferences("roohi_fallback_secure", Context.MODE_PRIVATE)
        }
    }

    fun saveCredential(key: String, value: String) {
        securePrefs.edit().putString(key, value).apply()
    }

    fun getCredential(key: String): String? {
        return securePrefs.getString(key, null)
    }

    fun deleteCredential(key: String) {
        securePrefs.edit().remove(key).apply()
    }

    fun clearAllCredentials() {
        securePrefs.edit().clear().apply()
    }
}
