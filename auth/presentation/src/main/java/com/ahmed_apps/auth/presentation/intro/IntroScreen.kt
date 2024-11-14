package com.ahmed_apps.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_apps.auth.presentation.R
import com.ahmed_apps.core.presentation.designsystem.LogoIcon
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import com.ahmed_apps.core.presentation.designsystem.components.GradientBackground
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerActionButton
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerOutlinedActionButton

/**
 * @author Ahmed Guedmioui
 */
@Composable
fun IntroScreenCore(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {

    IntroScreen { introAction ->
        when (introAction) {
            IntroAction.OnSignInClick -> onSignInClick()
            IntroAction.OnSignUpClick -> onSignUpClick()
        }
    }
}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            RunningTrackerLogoVertical()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_to_running_tracker),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.running_tracker_description),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(32.dp))

            RunningTrackerOutlinedActionButton(
                text = stringResource(R.string.sing_in),
                isLoading = false,
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                onAction(IntroAction.OnSignInClick)
            }

            Spacer(modifier = Modifier.height(16.dp))

            RunningTrackerActionButton(
                text = stringResource(R.string.sign_up),
                isLoading = false,
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                onAction(IntroAction.OnSignUpClick)
            }

        }
    }

}

@Composable
private fun RunningTrackerLogoVertical(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    RunningTrackerTheme {
        IntroScreen {}
    }
}










