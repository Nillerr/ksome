package io.github.nillerr.ksome.core

/**
 * Wraps any value
 */
sealed class Maybe<out T>

data class Some<out T>(val value: T) : Maybe<T>() {
    companion object {
        fun <T> none(): Maybe<T> = None
    }
}

object None : Maybe<Nothing>()

inline fun <T, R> Maybe<T>.flatMap(block: (T) -> Maybe<R>): Maybe<R> = when (this) {
    is Some -> block(value)
    is None -> this
}

inline fun <T, R> Maybe<T>.map(block: (T) -> R): Maybe<R> = flatMap { Some(block(it)) }

fun <T> Maybe<Maybe<T>>.flatten(): Maybe<T> = flatMap { it }

fun <T> Maybe<T>.toList(): List<T> = when (this) {
    is Some -> listOf(value)
    is None -> emptyList()
}
