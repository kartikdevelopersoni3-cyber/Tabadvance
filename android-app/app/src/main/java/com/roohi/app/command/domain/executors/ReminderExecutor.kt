package com.roohi.app.command.domain.executors

import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderExecutor @Inject constructor() {
    private val fakeStorage = CopyOnWriteArrayList<String>()

    fun execute(actionText: String): Pair<Boolean, String?> {
        fakeStorage.add(actionText)
        return Pair(true, null) 
    }
}
