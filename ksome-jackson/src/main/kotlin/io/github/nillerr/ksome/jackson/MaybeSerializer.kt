package io.github.nillerr.ksome.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import io.github.nillerr.ksome.core.Maybe
import io.github.nillerr.ksome.core.None
import io.github.nillerr.ksome.core.Some

/**
 * Serializes a [Maybe] by ignoring any top-level `null` values, while serializing any non-null values using the
 * value serializer.
 */
class MaybeSerializer : JsonSerializer<Maybe<*>>(), ContextualSerializer {
    private lateinit var valueSerializer: JsonSerializer<Any?>

    override fun createContextual(provider: SerializerProvider, property: BeanProperty): JsonSerializer<*> {
        val valueType = property.type.containedType(0)
        val innerProperty = InnerBeanProperty(valueType, property)
        val valueSerializer = provider.findValueSerializer(valueType, innerProperty)

        val serializer = MaybeSerializer()
        serializer.valueSerializer = valueSerializer
        return serializer
    }

    override fun serialize(value: Maybe<*>, generator: JsonGenerator, serializers: SerializerProvider) {
        val some = value as Some<*>

        when (val v = some.value) {
            null -> generator.writeNull()
            else -> valueSerializer.serialize(v, generator, serializers)
        }
    }

    override fun isEmpty(provider: SerializerProvider, value: Maybe<*>): Boolean {
        return value == None
    }

    private class InnerBeanProperty(private val valueType: JavaType, property: BeanProperty) : BeanProperty by property {
        override fun getType(): JavaType {
            return valueType
        }
    }
}
