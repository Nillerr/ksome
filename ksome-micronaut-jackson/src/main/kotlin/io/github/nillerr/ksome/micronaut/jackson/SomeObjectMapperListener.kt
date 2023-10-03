package io.github.nillerr.ksome.micronaut.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.nillerr.ksome.core.Some
import io.github.nillerr.ksome.jackson.registerSomeModule
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import jakarta.inject.Singleton

/**
 * Configures the [ObjectMapper] with support for the [Some] type.
 */
@Singleton
class SomeObjectMapperListener : BeanCreatedEventListener<ObjectMapper> {
    override fun onCreated(event: BeanCreatedEvent<ObjectMapper>): ObjectMapper {
        val mapper = event.bean
        mapper.registerSomeModule()
        return mapper
    }
}
