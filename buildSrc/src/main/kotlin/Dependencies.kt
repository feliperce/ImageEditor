object Dependencies {
    object Androidx {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.composeUi}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.composeTooling}"
            const val foundation =
                "androidx.compose.foundation:foundation:${Versions.composeFoundation}"
            const val material = "androidx.compose.material:material:${Versions.composeMaterial}"
            const val materialIconsCore =
                "androidx.compose.material:material-icons-core:${Versions.composeMaterialIconsCore}"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.composeMaterialIconsExtended}"
            const val runtimeLivedata =
                "androidx.compose.runtime:runtime-livedata:${Versions.composeRuntimeLivedata}"
            const val junit4 = "androidx.compose.ui:ui-test-junit4:${Versions.composeJunit4}"
        }
    }

    object Jetbrains {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Junit {
        const val junit = "junit:junit:${Versions.jUnit}"
    }
}