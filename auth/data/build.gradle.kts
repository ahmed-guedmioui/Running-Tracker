plugins {
    alias(libs.plugins.runningTrackerApp.android.library)
    alias(libs.plugins.runningTrackerApp.jvm.ktor)
}

android {
    namespace = "com.ahmed_apps.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    // Koin
    implementation(libs.bundles.koin)
}