package com.roohi.app.evolution.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EvolutionManager @Inject constructor(
    private val projectAnalyzer: ProjectAnalyzer,
    private val dependencyAnalyzer: DependencyAnalyzer,
    private val refactorPlanner: RefactorPlanner,
    private val patchGenerator: PatchGenerator,
    private val migrationGenerator: MigrationGenerator,
    private val logger: Logger
) {
    suspend fun initiateEvolutionCycle(targetModule: String) {
        logger.w("EvolutionManager", "Initiating Evolution Cycle for $targetModule (Owner Approval Required)")
        
        projectAnalyzer.scanProject()
        dependencyAnalyzer.mapDependencies()
        
        val plan = refactorPlanner.planRefactoring(targetModule)
        val patch = patchGenerator.generatePatch(plan)
        
        logger.i("EvolutionManager", "Evolution staging complete. Awaiting owner consent to apply $patch.")
    }
}
