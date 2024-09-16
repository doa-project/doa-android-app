// Gradle build file
buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15") // Use the latest version
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}