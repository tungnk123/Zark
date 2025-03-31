// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.55" apply false
    kotlin("plugin.serialization") version "2.1.10" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    kotlin("kapt") version "2.1.10"
}