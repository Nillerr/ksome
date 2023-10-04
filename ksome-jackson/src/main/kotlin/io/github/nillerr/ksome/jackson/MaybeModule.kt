package io.github.nillerr.ksome.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.github.nillerr.ksome.core.Maybe

private class MaybeModule : SimpleModule() {
    init {
        addSerializer(Maybe::class.java, MaybeSerializer())
        addDeserializer(Maybe::class.java, MaybeDeserializer())
    }
}

fun ObjectMapper.registerMaybeModule(): ObjectMapper {
    return also {
        registerModule(MaybeModule())

        configOverride(Maybe::class.java)
            .setIncludeAsProperty(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, null))
    }
}
