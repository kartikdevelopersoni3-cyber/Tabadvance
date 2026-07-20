package com.roohi.app.conversation.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FallbackEngine @Inject constructor() {
    
    fun generateClarificationRequest(): String {
        val choices = listOf(
            "I didn't quite catch that. Could you repeat?",
            "Sorry, could you rephrase that for me?",
            "I'm not sure I understood. What did you mean?",
            "Maaf karna, main samjhi nahi. Kya aap wapas bol sakte hain?" // Hinglish fallback
        )
        return choices.random()
    }
    
    fun generateErrorResponse(): String {
        return "I am experiencing an internal error and cannot process your request right now."
    }
}
