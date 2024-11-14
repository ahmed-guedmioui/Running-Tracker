package com.ahmed_apps.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahmed_apps.core.notification.ActiveRunService
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import com.ahmed_apps.core.presentation.designsystem.StartIcon
import com.ahmed_apps.core.presentation.designsystem.StopIcon
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerActionButton
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerDialog
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerFloatingActionButton
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerOutlinedActionButton
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerScaffold
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerToolbar
import com.ahmed_apps.core.presentation.ui.ObserveAsEvent
import com.ahmed_apps.run.presentation.R
import com.ahmed_apps.run.presentation.active_run.components.RunDataCard
import com.ahmed_apps.run.presentation.active_run.maps.TrackerMap
import com.ahmed_apps.run.presentation.util.hasLocationPermission
import com.ahmed_apps.run.presentation.util.hasNotificationPermission
import com.ahmed_apps.run.presentation.util.shouldShowLocationPermissionRationale
import com.ahmed_apps.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream

/**
 * @author Ahmed Guedmioui
 */
@Composable

fun ActiveRunScreenCore(
    onFinished: () -> Unit,
    onBack: () -> Unit,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.events) { event ->
        when (event) {
            is ActiveRunEvent.Error -> {
                Toast.makeText(
                    context, event.error.asString(context), Toast.LENGTH_LONG
                ).show()
            }

            ActiveRunEvent.RunSaved -> {
                onFinished()
            }
        }
    }

    ActiveRunScreen(
        state = viewModel.state,
        onServiceToggle = onServiceToggle,
        onAction = { action ->
            when(action) {
                is ActiveRunAction.OnBackClick -> {
                    if (!viewModel.state.hasStartedRunning) {
                        onBack()
                    }
                }

                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onAction: (ActiveRunAction) -> Unit
) {

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                perms[Manifest.permission.POST_NOTIFICATIONS] == true
            } else {
                true
            }

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationRational = showLocationRationale
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationRational = showNotificationRationale
            )
        )
    }

    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRational = showLocationRationale
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationRational = showNotificationRationale
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRunningTrackerPermissions(context)
        }
    }

    LaunchedEffect(state.isRunFinished) {
        if (state.isRunFinished) {
            onServiceToggle(false)
        }
    }

    val isServiceActive by ActiveRunService.isServiceActive.collectAsStateWithLifecycle()
    LaunchedEffect(state.shouldTrack, isServiceActive) {
        if (context.hasLocationPermission() && state.shouldTrack && !isServiceActive) {
            onServiceToggle(true)
        }
    }

    RunningTrackerScaffold(
        withGradient = false,
        topAppBar = {
            RunningTrackerToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            RunningTrackerFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(R.string.pause_run)
                } else {
                    stringResource(R.string.start_run)
                }
            ) {
                onAction(ActiveRunAction.OnToggleRunClick)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {

            TrackerMap(
                modifier = Modifier.fillMaxSize(),
                isRunFinished = state.isRunFinished,
                currentLocation = state.currentLocation,
                locations = state.runData.locations,
                onSnapshot = { bitmap ->
                    val stream = ByteArrayOutputStream()
                    stream.use {
                        bitmap.compress(
                            Bitmap.CompressFormat.PNG, 80, it
                        )
                    }
                    onAction(ActiveRunAction.OnRunProcessed(stream.toByteArray()))
                }
            )

            RunDataCard(
                runData = state.runData,
                elapsedTime = state.elapsedTime,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(paddingValues)
                    .fillMaxWidth()
            )
        }
    }

    if (!state.shouldTrack && state.hasStartedRunning) {
        RunningTrackerDialog(
            title = stringResource(R.string.running_is_paused),
            description = stringResource(R.string.resume_or_finish_run),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            primaryButton = {
                RunningTrackerActionButton(
                    text = stringResource(R.string.resume),
                    onClick = {
                        onAction(ActiveRunAction.OnResumeRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            },
            secondaryButton = {
                RunningTrackerOutlinedActionButton(
                    text = stringResource(R.string.finish),
                    isLoading = state.isSavingRun,
                    onClick = {
                        onAction(ActiveRunAction.OnFinishRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        )
    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        RunningTrackerDialog(
            title = stringResource(R.string.permission_required),
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            onDismiss = { /* normal dismissing not allowed for permissions */ },
            primaryButton = {
                RunningTrackerOutlinedActionButton(
                    text = stringResource(R.string.okay),
                    onClick = {
                        onAction(ActiveRunAction.DismissRationalDialog)
                        permissionLauncher.requestRunningTrackerPermissions(context)
                    }
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRunningTrackerPermissions(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val notificationPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        arrayOf()
    }

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermissions)
        }

        !hasLocationPermission -> {
            launch(locationPermissions)
        }

        !hasNotificationPermission -> {
            launch(notificationPermissions)
        }
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RunningTrackerTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onServiceToggle = {},
            onAction = {}
        )
    }
}









