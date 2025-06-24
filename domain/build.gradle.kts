plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
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

    // Coroutines
    implementation(libs.coroutines.core)

    // MockK for mocking
    testImplementation(libs.mockk)

    // JUnit for testing
    testImplementation(libs.junit)

    // Coroutines for testing
    testImplementation(libs.coroutines.test)
}