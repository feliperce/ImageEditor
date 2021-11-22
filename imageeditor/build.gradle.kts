plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.Jetbrains.kotlin)
    implementation(Dependencies.Androidx.appcompat)
    implementation(Dependencies.Androidx.constraintLayout)
    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.testRunner)
    androidTestImplementation(Dependencies.Androidx.espressoCore)
    implementation(Dependencies.Androidx.recyclerView)
    implementation(Dependencies.Androidx.lifecycle)

    implementation(Dependencies.Androidx.Compose.ui)
    // Tooling support (Previews, etc.)
    implementation(Dependencies.Androidx.Compose.tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(Dependencies.Androidx.Compose.foundation)
    // Material Design
    implementation(Dependencies.Androidx.Compose.material)
    // Material design icons
    implementation(Dependencies.Androidx.Compose.materialIconsCore)
    implementation(Dependencies.Androidx.Compose.materialIconsExtended)
    // Integration with observables
    implementation(Dependencies.Androidx.Compose.runtimeLivedata)
    // UI Tests
    androidTestImplementation(Dependencies.Androidx.Compose.junit4)

}
