package com.roohi.app.knowledge.domain

import com.roohi.app.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KnowledgeFusionEngine @Inject constructor(private val logger: Logger) {
    fun fuse() { logger.d("KnowledgeFusionEngine", "Fusing cross-domain memory/learning facts") }
}

@Singleton
class KnowledgeManager @Inject constructor(
    private val knowledgeGraphEngine: KnowledgeGraphEngine,
    private val knowledgeRetriever: KnowledgeRetriever,
    private val knowledgeFusionEngine: KnowledgeFusionEngine,
    private val logger: Logger
) {
    suspend fun learnFact(fact: String) {
        logger.i("KnowledgeManager", "Learning new fact into graph: $fact")
        knowledgeGraphEngine.processGraphFact(fact, "RELATES_TO", "Context")
        knowledgeFusionEngine.fuse()
    }
    
    suspend fun queryKnowledge(query: String): String {
        return knowledgeRetriever.retrieveContext(query)
    }
}
