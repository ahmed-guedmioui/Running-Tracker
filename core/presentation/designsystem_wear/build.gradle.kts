plugins {
    alias(libs.plugins.runningTrackerApp.android.library.compose)
}

android {
    namespace = "com.ahmed_apps.core.presentation.designsystem_wear"
    defaultConfig {
        minSdk = 30
    }
}

dependencies {
    api(projects.core.presentation.designsystem)

    implementation(libs.androidx.wear.compose.material)
}