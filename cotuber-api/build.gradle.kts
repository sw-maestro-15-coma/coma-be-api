tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

tasks.getByName("bootBuildImage") {
    enabled = true
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":support:logging"))
    implementation(project(":support:jwt"))
    implementation(project(":support:util"))
    implementation(project(":storage:db"))
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("org.springframework.security:spring-security-test")
}
