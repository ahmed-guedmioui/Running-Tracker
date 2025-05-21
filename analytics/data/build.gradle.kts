plugins {
    alias(libs.plugins.runningTrackerApp.android.library)
    alias(libs.plugins.runningTrackerApp.android.room)
}

android {
    namespace = "com.ahmed_apps.analytics.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.koin)

    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)
}