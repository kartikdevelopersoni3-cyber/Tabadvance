package com.roohi.app.conversation.domain

import com.roohi.app.conversation.domain.models.IntentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseGenerator @Inject constructor() {

    fun generateOfflineStubResponse(intent: IntentType, inputText: String): String {
        return when (intent) {
            IntentType.CASUAL_CONVERSATION -> "Hello! Kaise ho aap? Main apki offline Roohi assistant hoon."
            IntentType.DEVICE_CONTROL_REQUEST -> "I am currently in offline mode, but I have registered your command to control the device."
            IntentType.CODING_REQUEST -> "You're asking about code: '\$inputText'. Once I connect to my LLM backend, I'll be able to help you write and debug that."
            IntentType.SEARCH_REQUEST -> "You want to search for something. Connect me to the internet, and I'll find that for you."
            IntentType.QUESTION -> "That's an interesting question. I'll need my full conversational brain (Gemini) loaded to give you a proper answer."
            IntentType.COMMAND -> "Command received. Offline processing complete."
            IntentType.UNKNOWN_INTENT -> "Hmm, I'm not sure I understand completely yet."
        }
    }
}
