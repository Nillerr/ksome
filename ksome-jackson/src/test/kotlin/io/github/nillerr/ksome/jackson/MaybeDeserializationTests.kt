package io.github.nillerr.ksome.jackson

import io.github.nillerr.ksome.core.Maybe
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaybeDeserializationTests {
    companion object {
        private val mapper = ObjectMapper()
            .findAndRegisterModules()
            .registerMaybeModule()
    }

    data class Container(
        val name: Maybe<String?>? = null,
        val age: Maybe<Int>? = null,
    )

    @Test
    fun `deserialization of Some value`() {
        // Given
        val input = """{"name":"Chrissy"}"""

        // When
        val output = mapper.readValue(input, Container::class.java)

        // Then
        assertEquals(Container(name = Maybe("Chrissy")), output)
    }

    @Test
    fun `deserialization of null value`() {
        // Given
        val input = """{"name":null}"""

        // When
        val output = mapper.readValue(input, Container::class.java)

        // Then
        assertEquals(Container(name = Maybe(null)), output)
    }

    @Test
    fun `deserialization of None value`() {
        // Given
        val input = """{}"""

        // When
        val output = mapper.readValue(input, Container::class.java)

        // Then
        assertEquals(Container(name = null), output)
    }
}
