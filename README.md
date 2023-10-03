# KSome - Some for Kotlin

Provides the `Some<T>` type used for patching values in `PATCH` APIs. The `Some<T>` type is like an `Optional<T>` that 
allows nullable values while carrying their nullability in Kotlin. It aims to solve the problem of detecting the 
different between a property being present (e.g. `undefined`) and it being present but `null`.

## Installation (Micronaut)

To install KSome in Micronaut we typically require the following 3 dependencies:

```kotlin
dependencies {
    val ksome_version = "1.0.0"
    implementation("io.github.nillerr:ksome-core:$ksome_version")
    implementation("io.github.nillerr:ksome-micronaut-jackson:$ksome_version")
    implementation("io.github.nillerr:ksome-micronaut-validation:$ksome_version")
}
```

## Installation (Spring)

To install KSome in Spring we typically require the following 3 dependencies:

```kotlin
dependencies {
    val ksome_version = "1.0.0"
    implementation("io.github.nillerr:ksome-core:$ksome_version")
    implementation("io.github.nillerr:ksome-spring-jackson:$ksome_version")
    implementation("io.github.nillerr:ksome-spring-validation:$ksome_version")
}
```

## Installation (Standalone)

To install KSome in a library we typically require the following 2 dependencies:

```kotlin
dependencies {
    val ksome_version = "1.0.0"
    implementation("io.github.nillerr:ksome-core:$ksome_version")
    implementation("io.github.nillerr:ksome-jakarta-validation:$ksome_version")
}
```

## Usage

To use KSome we declare properties in a `data class` using as nullable `Some<T>` with a default value of `null`, like 
so:

```kotlin
data class PatchPersonRequest(
    val name: Some<String>? = null,
    val job: Some<String?>? = null,
    val age: Some<Int>? = null,
)
```

In this case, the `job` property is nullable indicating you can explicitly set the property to `null`, where-as the 
`name` and `age` properties type arguments are not nullable, indicating these must be non-null when present. While this 
ought to be enough, the generic type system is mostly an illusion at runtime, so to get property validation of the 
non-nullability of these properties, we must add some form of validation, e.g. the `ksome-jakarta-validation` library, 
which provides integration with `jakarta.validation`. The proper form of `PatchPersonRequest` would thus look like this:

```kotlin
data class PatchPersonRequest(
    @field:NotBlank
    val name: Some<String>? = null,
    val job: Some<String?>? = null,
    
    @field:NotNull
    @field:Min(18)
    val age: Some<Int>? = null,
)
```
