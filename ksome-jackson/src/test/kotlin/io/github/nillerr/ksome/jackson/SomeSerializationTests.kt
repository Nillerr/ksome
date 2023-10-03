package io.github.nillerr.ksome.jackson

import io.github.nillerr.ksome.core.Some
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SomeSerializationTests {
    private val mapper = ObjectMapper()
        .registerSomeModule()

    data class Container(
        val name: Some<String?>? = null,
    )

    @Test
    fun `serialization of Some value`() {
        // Given
        val input = Container(name = Some("Chrissy"))

        // When
        val output = mapper.writeValueAsString(input)

        // Then
        assertEquals("""{"name":"Chrissy"}""", output)
    }

    @Test
    fun `serialization of null value`() {
        // Given
        val input = Container(name = Some(null))

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
