package io.github.nillerr.ksome.javax.validation

import io.github.nillerr.ksome.core.Maybe
import io.github.nillerr.ksome.core.None
import io.github.nillerr.ksome.core.Some
import javax.validation.valueextraction.UnwrapByDefault
import javax.validation.valueextraction.ValueExtractor

@UnwrapByDefault
class MaybeValueExtractor : ValueExtractor<Maybe<*>> {
    override fun extractValues(originalValue: Maybe<*>, receiver: ValueExtractor.ValueReceiver) {
        if (originalValue is Some) {
            receiver.value(null, originalValue.value)
        }
    }
}

@UnwrapByDefault
class SomeValueExtractor : ValueExtractor<Some<*>> {
    override fun extractValues(originalValue: Some<*>, receiver: ValueExtractor.ValueReceiver) {
        receiver.value(null, originalValue.value)
    }
}

@UnwrapByDefault
class NoneValueExtractor : ValueExtractor<None> {
    override fun extractValues(originalValue: None?, receiver: ValueExtractor.ValueReceiver) {
        // Nothing
    }
}
