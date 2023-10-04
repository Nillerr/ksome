package io.github.nillerr.ksome.micronaut.validation

import io.github.nillerr.ksome.core.Maybe
import io.github.nillerr.ksome.core.None
import io.github.nillerr.ksome.core.Some
import io.github.nillerr.ksome.javax.validation.MaybeValueExtractor
import io.github.nillerr.ksome.javax.validation.NoneValueExtractor
import io.github.nillerr.ksome.javax.validation.SomeValueExtractor
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import javax.validation.valueextraction.ValueExtractor

@Factory
class SomeMicronautValidationFactory {
    @Singleton
    fun maybeValueExtractor(): ValueExtractor<Maybe<*>> {
        return MaybeValueExtractor()
    }

    @Singleton
    fun someValueExtractor(): ValueExtractor<Some<*>> {
        return SomeValueExtractor()
    }

    @Singleton
    fun noneValueExtractor(): ValueExtractor<None> {
        return NoneValueExtractor()
    }
}
