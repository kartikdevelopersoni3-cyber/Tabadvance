package com.roohi.app.voiceauth.domain.model

data class Voiceprint(
    val id: String,
    val ownerName: String,
    val embedding: FloatArray, // The high-dimensional feature vector of the user's voice
    val createdAt: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Voiceprint
        if (id != other.id) return false
        if (!embedding.contentEquals(other.embedding)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + embedding.contentHashCode()
        return result
    }
}
