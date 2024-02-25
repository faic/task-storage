group = "org.taskstorage"
version = "1.0-SNAPSHOT"

val springbootstarter = "2.7.18"
val junit = "5.10.1"
val mapstruct = "1.5.3.Final"
val flyway = "8.4.4"
val mockito = "3.6.0"
val mysql = "8.0.33"
var testcontainers = "1.19.6"

repositories {
    mavenCentral()
}

plugins {
    id("java")
    id("org.springframework.boot") version "2.7.18"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

apply(plugin = "io.spring.dependency-management")


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springbootstarter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springbootstarter")
    implementation("org.mapstruct:mapstruct:$mapstruct") // Adjust version if needed
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstruct")
    runtimeOnly("mysql:mysql-connector-java:$mysql")
    implementation("org.flywaydb:flyway-core:$flyway")
    implementation("org.flywaydb:flyway-mysql:$flyway")


    testImplementation("org.springframework.boot:spring-boot-starter-test:$springbootstarter")
    testImplementation(platform("org.junit:junit-bom:$junit"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junit")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockito")
    testImplementation("org.mockito:mockito-core:$mockito")
    testImplementation("mysql:mysql-connector-java:$mysql")
    testImplementation("org.testcontainers:mysql:$testcontainers")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainers")
}

tasks.test {
    useJUnitPlatform()
}