package io.github.nillerr.ksome.jackson

import io.github.nillerr.ksome.core.Some
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule

private class SomeModule : SimpleModule() {
    init {
        addSerializer(Some::class.java, SomeSerializer())
        addDeserializer(Some::class.java, SomeDeserializer())
    }
}

fun ObjectMapper.registerSomeModule(): ObjectMapper {
    return also {
        registerModule(SomeModule())

        configOverride(Some::class.java)
            .setIncludeAsProperty(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, null))
    }
}
