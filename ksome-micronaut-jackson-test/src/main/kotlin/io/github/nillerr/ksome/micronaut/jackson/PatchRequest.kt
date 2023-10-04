package io.github.nillerr.ksome.micronaut.jackson

import io.github.nillerr.ksome.core.Maybe
import io.github.nillerr.ksome.core.None
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class PatchRequest(
    @field:NotBlank
    val name: Maybe<String?> = None,

    @field:Min(18)
    @field:NotNull
    val age: Maybe<Int> = None,
)
