package io.github.nillerr.ksome.jackson

import io.github.nillerr.ksome.core.Maybe
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaybeSerializationTests {
    private val mapper = ObjectMapper()
        .registerMaybeModule()

    data class Container(
        val name: Maybe<String?>? = null,
    )

    @Test
    fun `serialization of Some value`() {
        // Given
        val input = Container(name = Maybe("Chrissy"))

        // When
        val output = mapper.writeValueAsString(input)

        // Then
        assertEquals("""{"name":"Chrissy"}""", output)
    }

    @Test
    fun `serialization of null value`() {
        // Given
        val input = Container(name = Maybe(null))

        // When
        val output = mapper.writeValueAsString(input)

        // Then
        assertEquals("""{"name":null}""", output)
    }

    @Test
    fun `serialization of None value`() {
        // Given
        val input = Container(name = null)

        // When
        val output = mapper.writeValueAsString(input)

        // Then
        assertEquals("""{}""", output)
    }
}
