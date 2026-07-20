# Module A Evidence Audit

## 1. ContextFusionEngine.kt
- Status: VERIFIED
- Path: `/android-app/app/src/main/java/com/roohi/app/reasoning/domain/ContextFusionEngine.kt`
- Package Name: `com.roohi.app.reasoning.domain`
- Class Name: `ContextFusionEngine`
- Interface Name: None
- Constructor Dependencies: `Logger`
- Hilt Binding: Natively injected (`@Inject constructor`), mapped in `SingletonComponent`
- Runtime Caller: `ReasoningManager`
- Runtime Callee: None
- Database Access Path: None
- Flow Usage: None
- Coroutine Scope Usage: Synchronous
- Execution Trigger: `ReasoningManager.performReasoningCheck()`
- Integration Chain: `ConversationManager` -> `ReasoningManager` -> `ContextFusionEngine`

## 2. PreferenceReasoner.kt
- Status: VERIFIED
- Path: `/android-app/app/src/main/java/com/roohi/app/reasoning/domain/PreferenceReasoner.kt`
- Package Name: `com.roohi.app.reasoning.domain`
- Class Name: `PreferenceReasoner`
- Interface Name: None
- Constructor Dependencies: `OwnerConfigurationManager`, `Logger`
- Hilt Binding: Natively injected (`@Inject constructor`), mapped in `SingletonComponent`
- Runtime Caller: `ReasoningManager`
- Runtime Callee: `OwnerConfigurationManager`
- Database Access Path: Flow State from Owner Profile Layer
- Flow Usage: Interacts with `StateFlow` from `OwnerConfigurationManager`
- Coroutine Scope Usage: Synchronous
- Execution Trigger: `ReasoningManager.performReasoningCheck()`
- Integration Chain: `ConversationManager` -> `ReasoningManager` -> `PreferenceReasoner` -> `OwnerConfigurationManager`

## 3. MemoryReasoner.kt
- Status: VERIFIED
- Path: `/android-app/app/src/main/java/com/roohi/app/reasoning/domain/MemoryReasoner.kt`
- Package Name: `com.roohi.app.reasoning.domain`
- Class Name: `MemoryReasoner`
- Interface Name: None
- Constructor Dependencies: `MemoryManager`, `Logger`
- Hilt Binding: Natively injected (`@Inject constructor`)
- Runtime Caller: `ReasoningManager`
- Runtime Callee: `MemoryManager`
- Database Access Path: Indirectly via `MemoryManager.retrieveRelevantContext` targeting `MemoryDatabase`
- Flow Usage: Suspend functions (Coroutines)
- Coroutine Scope Usage: Suspends on caller's Coroutine Scope
- Execution Trigger: `ReasoningManager.performReasoningCheck()`
- Integration Chain: `ConversationManager` -> `ReasoningManager` -> `MemoryReasoner` -> `MemoryManager`

## 4. ReasoningManager.kt
- Status: VERIFIED
- Path: `/android-app/app/src/main/java/com/roohi/app/reasoning/domain/ReasoningManager.kt`
- Package Name: `com.roohi.app.reasoning.domain`
- Class Name: `ReasoningManager`
- Interface Name: None
- Constructor Dependencies: `ContextReasoner`, `IntentFusionEngine`, `ContextFusionEngine`, `PreferenceReasoner`, `MemoryReasoner`, `GoalManager`, `TaskDecomposer`, `PlanningEngine`, `DecisionEngine`, `ActionPlanner`, `SessionReasoner`, `MemoryManager`, `PersonalityManager`, `OwnerConfigurationManager`, `Lazy<ConversationManager>`, `Logger`
- Hilt Binding: Natively injected (`@Inject constructor`)
- Runtime Caller: `ConversationManager`
- Runtime Callee: Multiple Reasoner Engines
- Database Access Path: Indirectly via `MemoryManager`
- Flow Usage: Emits to `ReasoningDiagnostics` StateFlow
- Coroutine Scope Usage: Initiates background evaluation scopes
- Execution Trigger: `ConversationManager.processMessage()`
- Integration Chain: `ConversationManager` -> `ReasoningManager` -> External Dependencies.
