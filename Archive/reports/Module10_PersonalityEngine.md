# Roohi Module 10: Personality Engine

## OVERVIEW
The Personality Engine transitions Roohi from a generic assistant into a recognizable character by injecting controlled, simulated emotional states and enforcing interaction consistency across conversations.

### CORE COMPONENTS

**PersonalityManager**: The orchestrator binding emotional subsets to memory bridges and style adaptations.
- File: `PersonalityManager.kt`
- Purpose: Updates internal emotional states based on Intent Classification, filters generated response outputs through `PersonalityMemoryBridge` and `ConversationStyleEngine`.

**PersonalityProfile**: Data model.
- File: `PersonalityModels.kt`
- Purpose: Contains default indices (friendlinessLevel: 8, formalityLevel: 5, etc.)

**EmotionSimulationEngine**: Thread-safe Flow observer.  
- File: `EmotionSimulationEngine.kt`
- Purpose: Emits `SimulatedEmotion` (NEUTRAL, HAPPY, EXCITED, EMERGENCY). Exposes `getEmotionContext()` directly into the conversational planner.

**ConversationStyleEngine**: 
- File: `ConversationStyleEngine.kt`
- Purpose: Maps the output text to simulate linguistic variations.

**OwnerRelationshipEngine**:
- File: `OwnerRelationshipEngine.kt`
- Purpose: Evaluates Owner Profile and adjusts responses to track interactions patterns securely.

**PersonalityMemoryBridge**:
- File: `PersonalityMemoryBridge.kt`
- Purpose: Joins offline Database retrieval sets with the transient emotional context map.

**TabletPresenceManager**:
- File: `TabletPresenceManager.kt`
- Purpose: Simulates physical presence queues when transitioning between idle and listening states on the tablet UI.

## INTEGRATION
The entire pipeline is connected directly to `ConversationManager.kt`. 
Graph Flow:
`Intent Classification` -> `Perform Reasoning` -> `Update Emotion State` -> `Execute Command` -> `Inject Personality via processResponse()` -> `TTS Output`.
