# Module H: Knowledge Engine

## Core Value
Roohi's memory converts isolated variables into relational Graph matrices securely.

## Subsystems Implemented
- **KnowledgeDataStore.kt**: Creates `KnowledgeEntity` & `RelationshipEntity`.
- **KnowledgeEngines.kt**: `KnowledgeGraphEngine`, `KnowledgeRelationshipEngine` properly handle the offline graph linking without web services.
- **KnowledgeManager.kt**: Handles high-level fusing via `KnowledgeFusionEngine`.
- **KnowledgeMonitors.kt**: Validates nodes securely preventing orphan references cleanly.

## Trace Path
`ReasoningManager` -> `AgentCoordinator` -> `KnowledgeAgent` -> `KnowledgeManager` -> `KnowledgeGraphEngine` -> `KnowledgeDataStore`.
