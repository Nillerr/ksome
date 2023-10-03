package io.github.nillerr.ksome.jackson

import io.github.nillerr.ksome.core.Some
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer

/**
 * Deserializes a [Some] by resolving `null` for absent values (`undefined`), while deserializing non-empty values
 * using the value deserializer.
 */
class SomeDeserializer : JsonDeserializer<Some<*>>(), ContextualDeserializer {
    private lateinit var valueType: JavaType
    private lateinit var valueDeserializer: JsonDeserializer<*>

    override fun createContextual(context: DeserializationContext, property: BeanProperty): JsonDeserializer<*> {
        val valueType = property.type.containedType(0)
        val innerProperty = InnerBeanProperty(valueType, property)
        val valueDeserializer = context.findContextualValueDeserializer(valueType, innerProperty)

        val deserializer = SomeDeserializer()
        deserializer.valueDeserializer = valueDeserializer
        deserializer.valueType = valueType
        return deserializer
    }

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Some<*> {
        val value = valueDeserializer.deserialize(parser, context)
        return Some(value)
    }

    override fun getNullValue(context: DeserializationContext?): Some<*> {
        val value = valueDeserializer.getNullValue(context)
        return Some(value)
    }

    override fun getAbsentValue(ctxt: DeserializationContext?): Any? {
        return null
    }

    private class InnerBeanProperty(private val valueType: JavaType, property: BeanProperty) : BeanProperty by property {
        override fun getType(): JavaType {
            return valueType
        }
    }
}
