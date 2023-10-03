import io.micronaut.gradle.MicronautRuntime
import io.micronaut.gradle.MicronautTestRuntime

plugins {
    id("io.micronaut.application")
}

micronaut {
    version.set("3.10.1")
    runtime.set(MicronautRuntime.NETTY)
    testRuntime.set(MicronautTestRuntime.JUNIT_5)

    processing {
        incremental.set(true)
        annotations.add("io.github.nillerr.ksome.micronaut.jackson.*")
    }
}

allOpen {
    annotation("io.micronaut.http.annotation.Controller")
    preset("micronaut")
}

application {
    mainClass.set("io.github.nillerr.ksome.micronaut.jackson.Application")
}

dependencies {
    implementation(project(":ksome-core"))
    implementation(project(":ksome-micronaut-jackson"))
    implementation(project(":ksome-micronaut-validation"))

    // Micronaut
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")

    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    // Logging
    runtimeOnly("ch.qos.logback:logback-classic")

    // Jackson
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Micronaut
    testImplementation("io.micronaut:micronaut-http-client")

    kaptTest("io.micronaut:micronaut-inject-java")
}
