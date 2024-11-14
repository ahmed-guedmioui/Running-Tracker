plugins {
    alias(libs.plugins.runningTrackerApp.android.library)
    alias(libs.plugins.runningTrackerApp.jvm.ktor)
}

android {
    namespace = "com.ahmed_apps.core.data"
}

dependencies {
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.core.database)

    // Koin
    implementation(libs.bundles.koin)
}