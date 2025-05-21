plugins {
    alias(libs.plugins.runningTrackerApp.jvm.library)
    alias(libs.plugins.runningTrackerApp.jvm.junit5)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(projects.core.domain)
    implementation(projects.core.connectivity.domain)
    testImplementation(projects.core.test)
}