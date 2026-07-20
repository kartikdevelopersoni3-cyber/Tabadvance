package com.roohi.app.core.error

/**
 * Base class for handling errors/failures throughout Roohi architecture cleanly.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object DatabaseError : Failure()
    data class CustomError(val errorCode: Int, val errorMessage: String?) : Failure()
    
    /** Extend this class for module-specific feature failures. */
    abstract class FeatureFailure : Failure()
}
