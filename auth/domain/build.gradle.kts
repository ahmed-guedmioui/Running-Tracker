plugins {
    alias(libs.plugins.runningTrackerApp.jvm.library)
    alias(libs.plugins.runningTrackerApp.jvm.junit5)
}

dependencies {
    implementation(projects.core.domain)
}