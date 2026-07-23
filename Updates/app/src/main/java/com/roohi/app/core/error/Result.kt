package com.roohi.app.core.error

/**
 * Represents a value of one of two possible types.
 * Instances of [Result] are either an instance of [Left] or [Right].
 * Used for functional error handling across Clean Architecture layers.
 */
sealed class Result<out L, out R> {
    data class Left<out L>(val a: L) : Result<L, Nothing>()
    data class Right<out R>(val b: R) : Result<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(fnL: (L) -> Unit, fnR: (R) -> Unit): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }
}
