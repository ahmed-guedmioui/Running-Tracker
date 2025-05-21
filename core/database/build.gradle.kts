plugins {
    alias(libs.plugins.runningTrackerApp.android.library)
    alias(libs.plugins.runningTrackerApp.android.room)
}

android {
    namespace = "com.ahmed_apps.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
}