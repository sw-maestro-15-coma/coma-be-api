val mockitoVersion = "5.12.0"

dependencies {
    implementation(project(":support:util"))
    compileOnly("org.springframework:spring-context")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.2")
}
