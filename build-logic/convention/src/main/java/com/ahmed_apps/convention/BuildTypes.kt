package com.ahmed_apps.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.DynamicFeatureExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.configure

/**
 * @author Ahmed Guedmioui
 */
internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir).getProperty("API_KEY")
        val mapsApiKey = gradleLocalProperties(rootDir).getProperty("MAPS_API_KEY")
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey, mapsApiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey, mapsApiKey)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey, mapsApiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey, mapsApiKey)
                        }
                    }
                }
            }

            ExtensionType.DYNAMIC_FEATURE -> {
                extensions.configure<DynamicFeatureExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey, mapsApiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey, mapsApiKey)
                            isMinifyEnabled = false
                        }
                    }
                }
            }
        }
    }
}


private fun BuildType.configureDebugBuildType(
    apiKey: String,
    mapsApiKey: String,
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *>,
    apiKey: String,
    mapsApiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}













