plugins {
    alias(libs.plugins.runningTrackerApp.android.feature.ui)
}

android {
    namespace = "com.ahmed_apps.analytics.presentation"
}

dependencies {

    implementation(projects.analytics.domain)
}