package io.github.nillerr.ksome.micronaut

import com.fasterxml.jackson.databind.ObjectMapper
import org.intellij.lang.annotations.Language
import kotlin.test.assertEquals

fun assertEqualsJson(@Language("JSON") expected: String, @Language("JSON") actual: String) {
    val mapper = ObjectMapper()

    val expectedNode = mapper.readTree(expected)
    val actualNode = mapper.readTree(actual)

    assertEquals(expectedNode, actualNode)
}
