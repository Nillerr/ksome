package io.github.nillerr.ksome.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import io.github.nillerr.ksome.core.Maybe
import io.github.nillerr.ksome.core.None
import io.github.nillerr.ksome.core.Some

/**
 * Deserializes a [Maybe] by resolving `null` for absent values (`undefined`), while deserializing non-empty values
 * using the value deserializer.
 */
class MaybeDeserializer : JsonDeserializer<Maybe<*>>(), ContextualDeserializer {
    private lateinit var valueType: JavaType
    private lateinit var valueDeserializer: JsonDeserializer<*>

    override fun createContextual(context: DeserializationContext, property: BeanProperty): JsonDeserializer<*> {
        val valueType = property.type.containedType(0)
        val innerProperty = InnerBeanProperty(valueType, property)
        val valueDeserializer = context.findContextualValueDeserializer(valueType, innerProperty)

        val deserializer = MaybeDeserializer()
        deserializer.valueDeserializer = valueDeserializer
        deserializer.valueType = valueType
        return deserializer
    }

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Maybe<*> {
        val value = valueDeserializer.deserialize(parser, context)
        return Some(value)
    }

    override fun getNullValue(context: DeserializationContext?): Maybe<*> {
        val value = valueDeserializer.getNullValue(context)
        return Some(value)
    }

    override fun getAbsentValue(ctxt: DeserializationContext?): Any {
        return None
    }

    override fun getEmptyValue(ctxt: DeserializationContext?): Any {
        return None
    }

    private class InnerBeanProperty(private val valueType: JavaType, property: BeanProperty) : BeanProperty by property {
        override fun getType(): JavaType {
            return valueType
        }
    }
}
