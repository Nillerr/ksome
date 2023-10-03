package io.github.nillerr.ksome.micronaut

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@MicronautTest
class TestControllerTests {
    @field:Inject
    @field:Client("/test")
    lateinit var client: HttpClient

    @Test
    fun `given invalid input, then validation errors are returned`() {
        // Given
        val requestBody = json(
            """
                {
                  "name": "",
                  "age": 17
                }
            """.trimIndent()
        )

        val httpRequest = HttpRequest.PATCH("/", requestBody)
            .contentType("application/json")
            .accept("application/json")

        // When
        val result = runCatching { client.toBlocking().retrieve(httpRequest) }

        // Then
        val exception = assertIs<HttpClientResponseException>(result.exceptionOrNull())
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)

        val responseBody = exception.response.getBody(String::class.java).get()
        val expectedResponseBody = json(
            """
                {
                  "message": "Bad Request",
                  "_embedded": {
                    "errors": [
                      { "message": "request.age: must be greater than or equal to 18" },
                      { "message": "request.name: must not be blank" }
                    ]
                  },
                  "_links": {
                    "self": {
                      "href": "/test",
                      "templated": false
                    }
                  }
                }
            """.trimIndent()
        )
        assertEqualsJson(expectedResponseBody, responseBody)
    }

    @Test
    fun `given invalid non-nullable, then validation errors are returned`() {
        // Given
        val requestBody = json(
            """
                {
                  "age": null
                }
            """.trimIndent()
        )

        val httpRequest = HttpRequest.PATCH("/", requestBody)
            .contentType("application/json")
            .accept("application/json")

        // When
        val result = runCatching { client.toBlocking().retrieve(httpRequest) }

        // Then
        val exception = assertIs<HttpClientResponseException>(result.exceptionOrNull())
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)

        val responseBody = exception.response.getBody(String::class.java).get()
        val expectedResponseBody = json(
            """
                {
                  "message": "Bad Request",
                  "_embedded": {
                    "errors": [
                      { "message": "request.age: must not be null" }
                    ]
                  },
                  "_links": {
                    "self": {
                      "href": "/test",
                      "templated": false
                    }
                  }
                }
            """.trimIndent()
        )
        assertEqualsJson(expectedResponseBody, responseBody)
    }

    @Test
    fun `given valid input, request is returned`() {
        // Given
        val requestBody = json(
            """
                {
                  "name": "Nicklas",
                  "age": 29
                }
            """.trimIndent()
        )

        val httpRequest = HttpRequest.PATCH("/", requestBody)
            .contentType("application/json")
            .accept("application/json")

        // When
        val responseBody = client.toBlocking().retrieve(httpRequest)

        // Then
        assertEqualsJson(requestBody, responseBody)
    }

    @Test
    fun `given valid partial input, request is returned`() {
        // Given
        val requestBody = json(
            """
                {
                  "name": "Nicklas"
                }
            """.trimIndent()
        )

        val httpRequest = HttpRequest.PATCH("/", requestBody)
            .contentType("application/json")
            .accept("application/json")

        // When
        val responseBody = client.toBlocking().retrieve(httpRequest)

        // Then
        assertEqualsJson(requestBody, responseBody)
    }
}
