package com.roohi.app.personality.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TabletPresenceManager @Inject constructor(
    private val logger: Logger
) {
    fun setIdleState() {
        logger.i("TabletPresenceManager", "Setting idle state presence.")
    }

    fun setListeningState() {
        logger.i("TabletPresenceManager", "Setting active listening presence.")
    }
}
