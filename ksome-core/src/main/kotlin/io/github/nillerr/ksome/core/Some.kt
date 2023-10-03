package io.github.nillerr.ksome.core

/**
 * Wraps any value
 */
data class Some<out T>(val value: T) {
    companion object {
        fun <T> none(): Some<T>? = null
    }
}

inline fun <T, R> Some<T>?.flatMap(block: (T) -> Some<R>?): Some<R>? = this?.let { block(value) }

inline fun <T, R> Some<T>?.map(block: (T) -> R): Some<R>? = flatMap { Some(block(it)) }

fun <T> Some<Some<T>?>?.flatten(): Some<T>? = flatMap { it }

fun <T> Some<T>?.toList(): List<T> = this?.let { listOf(value) } ?: emptyList()
