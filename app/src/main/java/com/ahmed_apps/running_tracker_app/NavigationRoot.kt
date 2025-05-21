package com.ahmed_apps.running_tracker_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.ahmed_apps.auth.presentation.intro.IntroScreenCore
import com.ahmed_apps.auth.presentation.login.LoginScreenCore
import com.ahmed_apps.auth.presentation.register.RegisterScreenCore
import com.ahmed_apps.core.notification.ActiveRunService
import com.ahmed_apps.run.presentation.active_run.ActiveRunScreenCore
import com.ahmed_apps.run.presentation.run_overview.RunOverviewScreenCore

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onAnalyticsClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "run" else "auth"
    ) {
        authGraph(navController)
        runGraph(
            navController = navController,
            onAnalyticsClick = onAnalyticsClick
        )
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            IntroScreenCore(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }

        composable(route = "register") {
            RegisterScreenCore(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )
        }

        composable(route = "login") {
            LoginScreenCore(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                inSignUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(
    navController: NavHostController,
    onAnalyticsClick: () -> Unit
) {
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable("run_overview") {
            RunOverviewScreenCore(
                onStartRunClick = {
                    navController.navigate("active_run")
                },
                onLogoutClick = {
                    navController.navigate("auth") {
                        popUpTo("run") {
                            inclusive = true
                        }
                    }
                },
                onAnalyticsClick = onAnalyticsClick
            )
        }
        composable(
            route = "active_run",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "running_tracker://active_run"
                }
            )
        ) {
            val context = LocalContext.current

            ActiveRunScreenCore(
                onFinished = {
                    navController.navigateUp()
                },
                onBack = {
                    navController.navigateUp()
                },
                onServiceToggle = { shouldRunService ->
                    if (shouldRunService) {
                        context.startService(
                            ActiveRunService.createStartIntent(
                                context = context,
                                activityClass = MainActivity::class.java
                            )
                        )
                    } else {
                        context.startService(
                            ActiveRunService.createStopIntent(
                                context = context
                            )
                        )
                    }
                }
            )
        }
    }
}


//@Composable
//fun NavigationRoot(
//    navController: NavHostController
//) {
//    NavHost(
//        navController = navController,
//        startDestination = FeatureScreens.Auth::class
//    ) {
//        authGraph(navController)
//    }
//}
//
//@SuppressLint("RestrictedApi")
//private fun NavGraphBuilder.authGraph(navController: NavHostController) {
//    navigation(
//        startDestination = AuthScreen.Intro,
//        route = FeatureScreens.Auth::class
//    ) {
//        composable<AuthScreen.Intro> {
//            IntroScreenRoot(
//                onSignUpClick = {
//                    navController.navigate(AuthScreen.Register)
//                },
//                onSignInClick = {
//                    navController.navigate(AuthScreen.Login)
//                }
//            )
//        }
//
//        composable<AuthScreen.Login> {
//
//        }
//
//        composable<AuthScreen.Register> {
//            RegisterScreenRoot(
//                onSignInClick = {
//                    navController.navigate(AuthScreen.Login) {
//                        popUpTo(AuthScreen.Register) {
//                            inclusive = true
//                            saveState = true
//                        }
//                    }
//                },
//                onSuccessfulRegistration = {
//                    navController.navigate(AuthScreen.Login)
//                }
//            )
//        }
//    }
//}
//
//sealed interface FeatureScreens {
//    @kotlinx.serialization.Serializable
//    data object Auth : FeatureScreens
//}
//
//sealed interface AuthScreen {
//    @kotlinx.serialization.Serializable
//    data object Intro : AuthScreen
//
//    @kotlinx.serialization.Serializable
//    data object Register : AuthScreen
//
//    @kotlinx.serialization.Serializable
//    data object Login : AuthScreen
//}


















