plugins {
    kotlin("kapt")
    kotlin("plugin.allopen")

    id("io.micronaut.library")
}

micronaut {
    version.set("3.10.1")

    processing {
        incremental.set(true)
        annotations.add("io.github.nillerr.ksome.micronaut.jackson.*")
    }
}

allOpen {
    preset("micronaut")
}

dependencies {
    implementation(project(":ksome-core"))
    implementation(project(":ksome-jackson"))

    // Micronaut
    kapt("io.micronaut:micronaut-inject-java")

    // Jackson
    val jackson_version = "2.15.2"
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
}
