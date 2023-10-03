package io.github.nillerr.ksome.micronaut.jackson

import io.github.nillerr.ksome.core.Some
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class PatchRequest(
    @field:NotBlank
    val name: Some<String?>? = null,

    @field:NotNull
    @field:Min(18)
    val age: Some<Int>? = null,
)
