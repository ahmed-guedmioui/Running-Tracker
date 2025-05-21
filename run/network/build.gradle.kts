plugins {
    alias(libs.plugins.runningTrackerApp.android.library)
    alias(libs.plugins.runningTrackerApp.jvm.ktor)
}

android {
    namespace = "com.ahmed_apps.run.network"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}