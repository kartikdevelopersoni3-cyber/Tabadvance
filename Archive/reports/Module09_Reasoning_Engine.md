# Roohi Module 09: Reasoning Engine

## PHASE 1: BUILD

### Implemented Components
**ReasoningManager**: Central reasoning coordinator. Implemented with suspend functionality wrapping child reasoning modules within `measureTimeMillis` for runtime health tracking.
- File: `ReasoningManager.kt`
- Hilt Binding: implicitly bounded by `@Inject constructor`, explicitly exported to others.
- Runtime Integration: Called directly inside `ConversationManager`.

**ContextReasoner**: Cross-references input against `MemoryRetrievalEngine` and `OwnerProfileManager`. 
- File: `ContextReasoner.kt`

**PlanningEngine**: Orchestrates complex interactions into JSON serialized DAG Step arrays.
- File: `PlanningEngine.kt`
- Maps: `PlanEntity`

**GoalManager**: Manages `GoalEntity`. Tracks completion and archiving.
- File: `GoalManager.kt`

**TaskDecomposer & DecisionEngine**: Stubbed local fallback engines handling input separation and decision evaluation. Designed to interface smoothly when moving towards actual LLM invocations.
- Files: `TaskDecomposer.kt`, `DecisionEngine.kt`

**PriorityEngine**: Validates tablet context mapping, ensuring keywords like "emergency" spike queue priority.
- File: `PriorityEngine.kt`

**ActionPlanner & IntentFusionEngine**: Fuses external context maps.
- Files: `ActionPlanner.kt`, `IntentFusionEngine.kt`

**SessionReasoner**: Tracks the last interaction time (`3600000ms` / 1 hour timeout), resuming interrupted execution graphs gracefully.
- File: `SessionReasoner.kt`

### Offline Data Foundations
**ReasoningDao / ReasoningDatabase / ReasoningRepositoryImpl**
- Stores `GoalEntity` and `PlanEntity` to allow the device to remember reasoning chains across process deaths.
- Room integration verified. `fallbackToDestructiveMigration()` applied.
- Files: `ReasoningDao.kt`, `ReasoningDatabase.kt`
