package com.ahmed_apps.running_tracker_app

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private lateinit var splitInstallManager: SplitInstallManager
    private val splitInstallListener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.INSTALLED -> {
                viewModel.setAnalyticsDialogVisibility(false)
                Toast.makeText(
                    applicationContext,
                    getString(R.string.analytics_feature_is_installed),
                    Toast.LENGTH_LONG
                ).show()
            }

            SplitInstallSessionStatus.INSTALLING -> {
                viewModel.setAnalyticsDialogVisibility(true)
            }

            SplitInstallSessionStatus.DOWNLOADING -> {
                viewModel.setAnalyticsDialogVisibility(true)
            }

            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                splitInstallManager.startConfirmationDialogForResult(state, this, 0)
            }

            SplitInstallSessionStatus.FAILED -> {
                viewModel.setAnalyticsDialogVisibility(false)
                Toast.makeText(
                    applicationContext,
                    getString(R.string.couldn_t_install_analytics_feature),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }

        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)

        setContent {
            RunningTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (!viewModel.state.isCheckingAuth) {
                        val navController = rememberNavController()
                        NavigationRoot(
                            navController = navController,
                            isLoggedIn = viewModel.state.isLoggedIn,
                            onAnalyticsClick = {
                                installOrStartAnalyticsFeature()
                            }
                        )

                        if (viewModel.state.showAnalyticsInstallDialog) {
                            Dialog(onDismissRequest = { /*TODO*/ }) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = stringResource(R.string.installing_module),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(splitInstallListener)
    }

    override fun onPause() {
        super.onPause()
        splitInstallManager.unregisterListener(splitInstallListener)
    }

    private fun installOrStartAnalyticsFeature() {
        if (splitInstallManager.installedModules.contains("analytics_feature")) {
            Intent()
                .setClassName(
                    packageName,
                    "com.ahmed_apps.analytics.analytics_feature.AnalyticsActivity"
                )
                .also(::startActivity)
            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addModule("analytics_feature")
            .build()
        splitInstallManager
            .startInstall(request)
            .addOnFailureListener {
                it.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    getString(R.string.couldn_t_load_analytics_feature),
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}
















