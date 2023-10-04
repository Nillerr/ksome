package io.github.nillerr.ksome.micronaut

import io.micronaut.http.client.exceptions.HttpClientResponseException
import kotlin.jvm.optionals.getOrNull
import kotlin.test.fail

inline fun <R> runLogging(block: () -> R): R {
    try {
        return block()
    } catch (e: HttpClientResponseException) {
        val message = buildString {
            appendLine(e.message)
            appendLine()
            appendLine(e.response.getBody(String::class.java).getOrNull() ?: "<empty>")
        }

        fail(message)
    }
}
