plugins {
    kotlin("kapt")
}

dependencies {
    // Projects
    implementation(project(":ksome-core"))

    // Jakarta
    implementation("jakarta.validation:jakarta.validation-api:2.0.1")
}
