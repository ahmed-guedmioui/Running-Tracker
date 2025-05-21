package com.ahmed_apps.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_apps.auth.domain.PasswordValidationState
import com.ahmed_apps.auth.domain.UserDataValidator
import com.ahmed_apps.auth.presentation.R
import com.ahmed_apps.core.presentation.designsystem.CheckIcon
import com.ahmed_apps.core.presentation.designsystem.CrossIcon
import com.ahmed_apps.core.presentation.designsystem.EmailIcon
import com.ahmed_apps.core.presentation.designsystem.Poppins
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerDarkRed
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerGreen
import com.ahmed_apps.core.presentation.designsystem.RunningTrackerTheme
import com.ahmed_apps.core.presentation.designsystem.components.GradientBackground
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerActionButton
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerPasswordTextField
import com.ahmed_apps.core.presentation.designsystem.components.RunningTrackerTextField
import com.ahmed_apps.core.presentation.ui.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel


/**
 * @author Ahmed Guedmioui
 */
@Composable
fun RegisterScreenCore(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvent(flow = viewModel.events) { event ->
        when(event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()

                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                ).show()

                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = {action ->
            when(action) {
                RegisterAction.OnLoginClick -> onSignInClick()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(R.string.login))
                    }
                }
            }
            ClickableText(text = annotatedString) { offset ->
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onAction(RegisterAction.OnLoginClick)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            RunningTrackerTextField(
                textFieldState = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(R.string.must_be_a_valid_email),
                keyBoardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            RunningTrackerPasswordTextField(
                textFieldState = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordRequirements(
                text = stringResource(
                    R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinimumLength
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirements(
                text = stringResource(R.string.at_least_one_number),
                isValid = state.passwordValidationState.hasDigit
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirements(
                text = stringResource(R.string.contains_a_lowercase_letter),
                isValid = state.passwordValidationState.hasLowercaseLetter
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirements(
                text = stringResource(R.string.contains_a_uppercase_letter),
                isValid = state.passwordValidationState.hasUppercaseLetter
            )
            Spacer(modifier = Modifier.height(32.dp))

            RunningTrackerActionButton(
                text = stringResource(R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                onAction(RegisterAction.ObRegisterClick)
            }
        }
    }
}

@Composable
fun PasswordRequirements(
    modifier: Modifier = Modifier,
    text: String,
    isValid: Boolean
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if (isValid) {
                RunningTrackerGreen
            } else {
                RunningTrackerDarkRed
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }

}

@Preview
@Composable
private fun RegisterScreenCoreScreenPreview() {
    RunningTrackerTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasDigit = true,
                ),
            ),
            onAction = {}
        )
    }
}








