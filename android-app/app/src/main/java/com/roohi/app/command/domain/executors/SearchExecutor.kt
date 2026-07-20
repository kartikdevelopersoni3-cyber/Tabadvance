package com.roohi.app.command.domain.executors

import android.content.Context
import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchExecutor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute(query: String): Pair<Boolean, String?> {
        if (query.isBlank()) return Pair(false, "Search query is empty.")
        return try {
            val encodedQuery = Uri.encode(query)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=$encodedQuery"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            context.startActivity(intent)
            Pair(true, null)
        } catch (e: ActivityNotFoundException) {
            Pair(false, "No browser found to handle search.")
        } catch (e: Exception) {
            Pair(false, "Failed to launch search: ${e.message}")
        }
    }
}
