package com.ahmed_apps.analytics.analytics_feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed_apps.analytics.data.di.analyticsDataModule
import com.ahmed_apps.analytics.presentation.AnalyticsDashboardScreenCore
import com.ahmed_apps.analytics.presentation.di.analyticsPresentationModule
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import com.google.android.play.core.splitcompat.SplitCompat
import org.koin.core.context.loadKoinModules

/**
 * @author Ahmed Guedmioui
 */
class AnalyticsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(
            listOf(
                analyticsDataModule, analyticsPresentationModule
            )
        )
        SplitCompat.installActivity(this)

        setContent {
            RunningTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "analytics_dashboard"
                ) {
                    composable("analytics_dashboard") {
                        AnalyticsDashboardScreenCore(
                            onBackClick = { finish() }
                        )
                    }
                }
            }
        }
    }

}















