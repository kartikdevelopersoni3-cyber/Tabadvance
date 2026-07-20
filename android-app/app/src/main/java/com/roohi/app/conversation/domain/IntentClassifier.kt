package com.roohi.app.conversation.domain

import com.roohi.app.conversation.domain.models.IntentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntentClassifier @Inject constructor() {

    // Offline stub implementation
    fun classifyIntent(inputText: String): Pair<IntentType, Float> {
        val lowerText = inputText.lowercase()
        
        // Return classified intent and a mock confidence score
        return when {
            lowerText.contains("turn on") || lowerText.contains("switch off") || lowerText.contains("volume") -> 
                Pair(IntentType.DEVICE_CONTROL_REQUEST, 0.9f)
            
            lowerText.contains("how to code") || lowerText.contains("write a python") || lowerText.contains("bug") -> 
                Pair(IntentType.CODING_REQUEST, 0.85f)
                
            lowerText.contains("search for") || lowerText.contains("who is") || lowerText.contains("what is") -> 
                Pair(IntentType.SEARCH_REQUEST, 0.8f)
                
            lowerText.startsWith("can you") || lowerText.contains("why") || lowerText.contains("how") -> 
                Pair(IntentType.QUESTION, 0.75f)
                
            lowerText.startsWith("hello") || lowerText.startsWith("hi") || lowerText.contains("how are you") -> 
                Pair(IntentType.CASUAL_CONVERSATION, 0.95f)
                
            lowerText.split(" ").size < 3 -> 
                Pair(IntentType.UNKNOWN_INTENT, 0.4f) // Low confidence for very short bursts not caught above
                
            else -> 
                Pair(IntentType.UNKNOWN_INTENT, 0.3f)
        }
    }
}
