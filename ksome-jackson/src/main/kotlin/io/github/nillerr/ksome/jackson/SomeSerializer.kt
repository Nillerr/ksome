package io.github.nillerr.ksome.jackson

import io.github.nillerr.ksome.core.Some
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer

/**
 * Serializes a [Some] by ignoring any top-level `null` values, while serializing any non-null values using the
 * value serializer.
 */
class SomeSerializer : JsonSerializer<Some<*>?>(), ContextualSerializer {
    private lateinit var valueSerializer: JsonSerializer<Any?>

    override fun createContextual(provider: SerializerProvider, property: BeanProperty): JsonSerializer<*> {
        val valueType = property.type.containedType(0)
        val innerProperty = InnerBeanProperty(valueType, property)
        val valueSerializer = provider.findValueSerializer(valueType, innerProperty)

        val serializer = SomeSerializer()
        serializer.valueSerializer = valueSerializer
        return serializer
    }

    override fun serialize(value: Some<*>?, generator: JsonGenerator, serializers: SerializerProvider) {
        if (value != null) {
            when (val v = value.value) {
                null -> generator.writeNull()
                else -> valueSerializer.serialize(v, generator, serializers)
            }
        }
    }

    override fun isEmpty(provider: SerializerProvider?, value: Some<*>?): Boolean {
        return value == null
    }

    private class InnerBeanProperty(private val valueType: JavaType, property: BeanProperty) : BeanProperty by property {
        override fun getType(): JavaType {
            return valueType
        }
    }
}
