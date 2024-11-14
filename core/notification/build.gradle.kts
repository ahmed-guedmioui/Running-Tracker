plugins {
    alias(libs.plugins.runningTrackerApp.android.library)
}

android {
    namespace = "com.ahmed_apps.core.notification"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)
}