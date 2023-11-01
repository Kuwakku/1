// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
}