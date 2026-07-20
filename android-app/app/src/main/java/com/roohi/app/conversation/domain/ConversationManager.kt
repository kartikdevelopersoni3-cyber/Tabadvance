package com.roohi.app.conversation.domain

import com.roohi.app.core.logging.Logger
import com.roohi.app.conversation.domain.models.ConversationMessage
import com.roohi.app.conversation.domain.models.IntentType
import com.roohi.app.command.domain.CommandExecutionManager
import com.roohi.app.memory.domain.MemoryManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

import com.roohi.app.speech.domain.TextToSpeechManager
import com.roohi.app.reasoning.domain.ReasoningManager
import com.roohi.app.personality.domain.PersonalityManager

@Singleton
class ConversationManager @Inject constructor(
    private val repository: ConversationRepository,
    private val contextManager: ContextManager,
    private val intentClassifier: IntentClassifier,
    private val responseGenerator: ResponseGenerator,
    private val fallbackEngine: FallbackEngine,
    private val commandExecutionManager: CommandExecutionManager,
    private val memoryManager: MemoryManager,
    private val textToSpeechManager: TextToSpeechManager,
    private val reasoningManager: ReasoningManager,
    private val personalityManager: PersonalityManager,
    private val logger: Logger
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val confidenceThreshold = 0.5f

    private val _isConversationActive = MutableStateFlow(false)
    val isConversationActive: StateFlow<Boolean> = _isConversationActive.asStateFlow()

    private val _currentIntent = MutableStateFlow(IntentType.UNKNOWN_INTENT)
    val currentIntent: StateFlow<IntentType> = _currentIntent.asStateFlow()

    private var sessionTimeoutJob: Job? = null
    private val SESSION_TIMEOUT_MS = 60_000L

    fun handleUserInput(text: String) {
        if (text.isBlank()) return
        
        logger.i("ConversationManager", "Handling user input: $text")
        startConversationSession()

        val userMessage = ConversationMessage(
            id = UUID.randomUUID().toString(),
            role = ConversationMessage.Role.USER,
            text = text,
            timestamp = System.currentTimeMillis()
        )

        scope.launch {
            try {
                repository.saveMessage(userMessage)

                // 1. Classify Intent
                val (intent, confidence) = intentClassifier.classifyIntent(text)
                _currentIntent.value = intent

                // Update Context
                contextManager.updateContext(text, intent)

                // Save to long-term memory engine
                memoryManager.addMemory(text, "ConversationManager", "session_voice")
                val memoryContext = memoryManager.retrieveRelevantContext(text)
                
                val replyText = if (confidence < confidenceThreshold) {
                    logger.w("ConversationManager", "Low confidence classified ($confidence). Yielding fallback.")
                    fallbackEngine.generateClarificationRequest()
                } else {
                    val reasoningOutput = reasoningManager.performReasoningCheck(text, intent.name)
                    personalityManager.updateEmotion(intent.name)
                    val cmdResponse = commandExecutionManager.executeCommand(text)
                    val baseResponse = cmdResponse ?: responseGenerator.generateOfflineStubResponse(intent, text)
                    val preFinalResponse = if (memoryContext.isNotBlank()) "$baseResponse (Plan: $reasoningOutput) (Mem: $memoryContext)" else "$baseResponse (Plan: $reasoningOutput)"
                    val finalResponse = personalityManager.processResponse(preFinalResponse, intent.name, memoryContext)
                    finalResponse
                }

                val assistantMessage = ConversationMessage(
                    id = UUID.randomUUID().toString(),
                    role = ConversationMessage.Role.ASSISTANT,
                    text = replyText,
                    timestamp = System.currentTimeMillis(),
                    intent = intent
                )

                repository.saveMessage(assistantMessage)
                logger.i("ConversationManager", "Response generated: $replyText")
                textToSpeechManager.speak(replyText)
                resetSessionTimeout()
            } catch (e: Exception) {
                logger.e("ConversationManager", "Error processing conversation", e)
                val errorMessageText = fallbackEngine.generateErrorResponse()
                val errorMessage = ConversationMessage(
                    id = UUID.randomUUID().toString(),
                    role = ConversationMessage.Role.SYSTEM,
                    text = errorMessageText,
                    timestamp = System.currentTimeMillis(),
                    intent = IntentType.UNKNOWN_INTENT
                )
                repository.saveMessage(errorMessage)
                textToSpeechManager.speak(errorMessageText)
            }
        }
    }

    private fun startConversationSession() {
        if (!_isConversationActive.value) {
            _isConversationActive.value = true
            logger.i("ConversationManager", "Conversation session started/activated.")
        }
        resetSessionTimeout()
    }

    private fun resetSessionTimeout() {
        sessionTimeoutJob?.cancel()
        sessionTimeoutJob = scope.launch {
            delay(SESSION_TIMEOUT_MS)
            logger.i("ConversationManager", "Conversation session timed out due to inactivity.")
            endConversation()
        }
    }

    fun endConversation() {
        if (_isConversationActive.value) {
            _isConversationActive.value = false
            logger.i("ConversationManager", "Conversation session ended.")
            sessionTimeoutJob?.cancel()
        }
    }

    fun clearSessionHistory() {
        scope.launch {
            repository.clearSession()
            contextManager.resetSession()
            logger.i("ConversationManager", "Session history cleared and context reset.")
        }
    }
}
