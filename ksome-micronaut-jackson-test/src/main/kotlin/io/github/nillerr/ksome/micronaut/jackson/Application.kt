package io.github.nillerr.ksome.micronaut.jackson

import io.micronaut.runtime.Micronaut

object Application {
    @JvmStatic
    fun main(vararg args: String) {
        Micronaut.run(*args)
    }
}
