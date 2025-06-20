plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("kapt")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation(project(":core"))

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.core)

    // MockK for mocking
    testImplementation(libs.mockk)

    // JUnit for testing
    testImplementation(libs.junit)

    // Coroutines for testing
    testImplementation(libs.coroutines.test)

    // Lifecycle Testing dependencies
    testImplementation(libs.arch.core.testing)
}