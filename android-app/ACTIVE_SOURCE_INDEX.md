# ACTIVE SOURCE INDEX

## New Files
- `app/src/main/java/com/roohi/app/coordination/domain/AgentModels.kt`
- `app/src/main/java/com/roohi/app/coordination/domain/AgentMessageBus.kt`
- `app/src/main/java/com/roohi/app/coordination/domain/AgentRegistry.kt`
- `app/src/main/java/com/roohi/app/coordination/domain/AgentCoordinator.kt`
- `app/src/main/java/com/roohi/app/coordination/agents/BaseAgent.kt`
- `app/src/main/java/com/roohi/app/coordination/agents/CoreAgents.kt`
- `app/src/main/java/com/roohi/app/coordination/monitor/AgentMonitors.kt`
- `app/src/main/java/com/roohi/app/coordination/di/CoordinationModule.kt`

## New Files (Module E)
- `app/src/main/java/com/roohi/app/vision/data/VisionDataStore.kt`
- `app/src/main/java/com/roohi/app/vision/domain/VisionComponents.kt`
- `app/src/main/java/com/roohi/app/vision/domain/VisionEngines.kt`
- `app/src/main/java/com/roohi/app/vision/monitor/VisionMonitors.kt`
- `app/src/main/java/com/roohi/app/vision/di/VisionModule.kt`

## New Files (Module F)
- `app/src/main/java/com/roohi/app/automation/data/AutomationDataStore.kt`
- `app/src/main/java/com/roohi/app/automation/domain/AutomationEngines.kt`
- `app/src/main/java/com/roohi/app/automation/domain/AutomationManagers.kt`
- `app/src/main/java/com/roohi/app/automation/monitor/AutomationMonitors.kt`
- `app/src/main/java/com/roohi/app/automation/di/AutomationModule.kt`

## New Files (Module G)
- `app/src/main/java/com/roohi/app/learning/data/LearningDataStore.kt`
- `app/src/main/java/com/roohi/app/learning/domain/LearningEngines.kt`
- `app/src/main/java/com/roohi/app/learning/domain/LearningComponents.kt`
- `app/src/main/java/com/roohi/app/learning/monitor/LearningMonitors.kt`
- `app/src/main/java/com/roohi/app/learning/di/LearningModule.kt`

## New Files (Module H)
- `app/src/main/java/com/roohi/app/knowledge/data/KnowledgeDataStore.kt`
- `app/src/main/java/com/roohi/app/knowledge/domain/KnowledgeEngines.kt`
- `app/src/main/java/com/roohi/app/knowledge/domain/KnowledgeManager.kt`
- `app/src/main/java/com/roohi/app/knowledge/monitor/KnowledgeMonitors.kt`
- `app/src/main/java/com/roohi/app/knowledge/di/KnowledgeModule.kt`

## New Files (Module I)
- `app/src/main/java/com/roohi/app/proactive/data/ProactiveDataStore.kt`
- `app/src/main/java/com/roohi/app/proactive/domain/ProactivePredictors.kt`
- `app/src/main/java/com/roohi/app/proactive/domain/ProactiveRecommendations.kt`
- `app/src/main/java/com/roohi/app/proactive/domain/ProactiveManager.kt`
- `app/src/main/java/com/roohi/app/proactive/monitor/ProactiveMonitors.kt`
- `app/src/main/java/com/roohi/app/proactive/di/ProactiveModule.kt`

## New Files (Module J)
- `app/src/main/java/com/roohi/app/workspace/data/WorkspaceDataStore.kt`
- `app/src/main/java/com/roohi/app/workspace/domain/WorkspaceEngines.kt`
- `app/src/main/java/com/roohi/app/workspace/domain/WorkspaceManagers.kt`
- `app/src/main/java/com/roohi/app/workspace/monitor/WorkspaceMonitors.kt`
- `app/src/main/java/com/roohi/app/workspace/di/WorkspaceModule.kt`

## New Files (Module Ω)
- `app/src/main/java/com/roohi/app/evolution/domain/EvolutionComponents.kt`
- `app/src/main/java/com/roohi/app/evolution/domain/EvolutionManager.kt`
- `app/src/main/java/com/roohi/app/evolution/monitor/EvolutionMonitors.kt`
- `app/src/main/java/com/roohi/app/evolution/di/EvolutionModule.kt`

## Modified Files
- `app/src/main/java/com/roohi/app/background/monitor/FoundationHealthMonitor.kt`
- `app/src/main/java/com/roohi/app/coordination/agents/CoreAgents.kt`
- `app/src/main/java/com/roohi/app/coordination/domain/AgentCoordinator.kt`

## Runtime Connections
- `DecisionEngine` -> `AgentCoordinator`
- `AgentCoordinator` -> `AgentMessageBus` -> `CoreAgents` -> Specialized Endpoints (`MemoryReasoner`, `DeviceControlManager`, `ExecutionPlanner`)
- `FoundationHealthMonitor` -> `AgentHealthMonitor` (Deadlock protection bounds)
