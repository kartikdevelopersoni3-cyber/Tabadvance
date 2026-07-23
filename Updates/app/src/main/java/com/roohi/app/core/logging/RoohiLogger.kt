package com.roohi.app.core.logging

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoohiLogger @Inject constructor() : Logger {
    override fun init() {
        Timber.plant(Timber.DebugTree())
    }

    override fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Timber.tag(tag).e(throwable, message)
    }

    override fun i(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }

    override fun w(tag: String, message: String) {
        Timber.tag(tag).w(message)
    }
}
