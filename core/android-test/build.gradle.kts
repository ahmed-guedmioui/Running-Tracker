plugins {
    alias(libs.plugins.runningTrackerApp.android.library.compose)
    alias(libs.plugins.runningTrackerApp.android.junit5)
}

android {
    namespace = "com.ahmed_apps.core.android_test"
}

dependencies {

    implementation(projects.auth.data)
    implementation(projects.core.domain)
    api(projects.core.test)

    implementation(libs.ktor.client.mock)
    implementation(libs.bundles.ktor)
    implementation(libs.coroutines.test)
}