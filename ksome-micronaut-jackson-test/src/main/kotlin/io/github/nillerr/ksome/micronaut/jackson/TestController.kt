package io.github.nillerr.ksome.micronaut.jackson

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Patch
import javax.validation.Valid

@Controller("/test")
class TestController {
    @Patch("/")
    fun patch(@Valid @Body request: PatchRequest): PatchRequest {
        return request
    }
}
