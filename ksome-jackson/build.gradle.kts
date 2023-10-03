dependencies {
    api(project(":ksome-core"))

    // Jackson
    val jackson_version = "2.15.2"
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")

    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jackson_version")
    testImplementation("com.fasterxml.jackson.module:jackson-module-parameter-names:$jackson_version")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")
}
