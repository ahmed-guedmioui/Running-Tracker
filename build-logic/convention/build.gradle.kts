@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
}

group = "com.ahmed_apps.running_tracker_app.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "running_tracker_app.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "running_tracker_app.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplicationWearCompose") {
            id = "running_tracker_app.android.application.wear.compose"
            implementationClass = "AndroidApplicationWearComposeConventionPlugin"
        }
        register("AndroidLibrary") {
            id = "running_tracker_app.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("AndroidLibraryCompose") {
            id = "running_tracker_app.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("AndroidFeatureUI") {
            id = "running_tracker_app.android.feature.ui"
            implementationClass = "AndroidFeatureUIConventionPlugin"
        }
        register("AndroidRoom") {
            id = "running_tracker_app.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("AndroidDynamicFeature") {
            id = "running_tracker_app.android.dynamic.feature"
            implementationClass = "AndroidDynamicFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "running_tracker_app.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor") {
            id = "running_tracker_app.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
        register("jvmJunit5") {
            id = "running_tracker_app.jvm.junit5"
            implementationClass = "JvmJUnit5ConventionPlugin"
        }
        register("androidJunit5") {
            id = "running_tracker_app.android.junit5"
            implementationClass = "AndroidJUnit5ConventionPlugin"
        }
    }
}










