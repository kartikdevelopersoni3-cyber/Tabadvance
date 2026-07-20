package com.roohi.app.core.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionsManager @Inject constructor() {

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(
        launcher: ActivityResultLauncher<Array<String>>,
        permissions: Array<String>
    ) {
        launcher.launch(permissions)
    }
}
