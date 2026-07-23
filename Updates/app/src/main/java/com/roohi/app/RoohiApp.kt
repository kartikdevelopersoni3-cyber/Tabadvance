package com.roohi.app

import android.app.Application
import com.roohi.app.core.logging.Logger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RoohiApp : Application() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        
        // App startup sequence initialized
        logger.init()
        logger.i("RoohiApp", "Application Created. Roohi System Core Foundation initialized.")
    }
}
