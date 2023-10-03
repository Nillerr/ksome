plugins {
    kotlin("kapt")

    id("io.micronaut.library")
}

micronaut {
    version.set("3.10.1")

    processing {
        incremental.set(true)
        annotations.add("io.github.nillerr.ksome.micronaut.validation.*")
    }
}

allOpen {
    preset("micronaut")
}

dependencies {
    // Projects
    implementation(project(":ksome-core"))
    implementation(project(":ksome-jakarta-validation"))

    // Jakarta
    implementation("jakarta.validation:jakarta.validation-api:2.0.1")

    // Micronaut
    api("io.micronaut:micronaut-validation")

    kapt("io.micronaut:micronaut-inject-java")
}
