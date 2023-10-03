rootProject.name = "ksome"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.allopen") version kotlinVersion apply false
        kotlin("kapt") version kotlinVersion apply false

        id("org.jetbrains.dokka") version kotlinVersion apply false

        id("io.micronaut.application") version "3.7.10" apply false
        id("io.micronaut.library") version "3.7.10" apply false
    }
}

include(":ksome-core")
include(":ksome-jackson")
include(":ksome-jakarta-validation")
include(":ksome-micronaut-jackson")
include(":ksome-micronaut-jackson-test")
include(":ksome-micronaut-validation")
include(":ksome-spring")
