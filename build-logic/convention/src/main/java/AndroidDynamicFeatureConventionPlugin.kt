import com.ahmed_apps.convention.ExtensionType
import com.ahmed_apps.convention.addUILayerDependencies
import com.ahmed_apps.convention.configureAndroidCompose
import com.ahmed_apps.convention.configureBuildTypes
import com.ahmed_apps.convention.configureKotlinAndroid
import com.android.build.api.dsl.DynamicFeatureExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * @author Ahmed Guedmioui
 */
class AndroidDynamicFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.dynamic-feature")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<DynamicFeatureExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.DYNAMIC_FEATURE
                )
            }

            dependencies {
                addUILayerDependencies(target)
                "testImplementation"(kotlin("test"))
            }
        }
    }

}
















