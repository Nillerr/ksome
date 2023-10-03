package io.github.nillerr.ksome.javax.validation

import io.github.nillerr.ksome.core.Some
import javax.validation.valueextraction.UnwrapByDefault
import javax.validation.valueextraction.ValueExtractor

@UnwrapByDefault
class SomeValueExtractor : ValueExtractor<Some<*>> {
    override fun extractValues(originalValue: Some<*>?, receiver: ValueExtractor.ValueReceiver) {
        if (originalValue != null) {
            receiver.value(null, originalValue.value)
        }
    }
}
