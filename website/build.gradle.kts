plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    application
}

group = "com.slava_110.website"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.slava_110.website.ApplicationKt")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.contentnegotiation)
    implementation(libs.ktor.server.serial.kotlinx)

    implementation(libs.ktor.server.metrics.micrometer)

    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.swagger)

    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.websockets)

    implementation(libs.logback)
    implementation(libs.micrometer.prometheus)
    implementation(libs.mongodb.driver.kotlin)
}