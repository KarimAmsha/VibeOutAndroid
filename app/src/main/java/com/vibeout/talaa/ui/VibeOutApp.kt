@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.ThemeMode
import com.vibeout.talaa.core.storage.AppPreferences
import com.vibeout.talaa.feature.auth.*
import com.vibeout.talaa.feature.home.*
import com.vibeout.talaa.feature.notifications.NotificationsScreen
import com.vibeout.talaa.feature.places.*
import com.vibeout.talaa.feature.profile.*
import com.vibeout.talaa.feature.safety.*
import com.vibeout.talaa.feature.vibes.*
import com.vibeout.talaa.ui.theme.VibeOutTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private object Routes {
    const val Splash = "splash"
    const val Onboarding = "onboarding"
    const val Login = "login"
    const val Register = "register"
    const val ForgotPassword = "forgot_password"
    const val ProfileSetup = "profile_setup"
    const val Home = "home"
    const val Places = "places"
    const val Map = "map"
    const val Place = "place/{placeId}"
    const val Vibes = "vibes"
    const val Vibe = "vibe/{vibeId}"
    const val CreateVibe = "create_vibe?placeId={placeId}"
    const val Chat = "chat/{vibeId}"
    const val Notifications = "notifications"
    const val Profile = "profile"
    const val Settings = "settings"
    const val Safety = "safety"
    const val BlockedUsers = "blocked_users"
    const val Plan = "plan/{planId}"
    const val Report = "report/{targetType}/{targetId}"
}

@HiltViewModel
class AppRootViewModel @Inject constructor(private val preferences: AppPreferences) : ViewModel() {
    val themeMode: StateFlow<ThemeMode> = preferences.themeMode.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        ThemeMode.SYSTEM,
    )

    fun completeOnboarding(onDone: () -> Unit) = viewModelScope.launch {
        preferences.setOnboardingDone(true)
        onDone()
    }
}

@Composable
fun VibeOutApp(rootViewModel: AppRootViewModel = hiltViewModel()) {
    val themeMode by rootViewModel.themeMode.collectAsState()
    val useDark = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
    }
    VibeOutTheme(darkTheme = useDark) {
        val navController = rememberNavController()
        val backStack by navController.currentBackStackEntryAsState()
        val currentRoute = backStack?.destination?.route
        val bottomRoutes = setOf(Routes.Home, Routes.Places, Routes.Vibes, Routes.Notifications, Routes.Profile)
        val showBottomBar = currentRoute in bottomRoutes

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        val tabs = listOf(
                            Triple(Routes.Home, R.string.home, Icons.Default.Home),
                            Triple(Routes.Places, R.string.places, Icons.Default.Place),
                            Triple(Routes.Vibes, R.string.vibes, Icons.Default.Groups),
                            Triple(Routes.Notifications, R.string.notifications, Icons.Default.Notifications),
                            Triple(Routes.Profile, R.string.profile, Icons.Default.Person),
                        )
                        tabs.forEach { (route, label, icon) ->
                            NavigationBarItem(
                                selected = currentRoute == route,
                                onClick = {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(icon, stringResource(label)) },
                                label = {
                                    Text(
                                        stringResource(label),
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        ) { outerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.Splash,
                modifier = Modifier.padding(outerPadding),
            ) {
                composable(Routes.Splash) {
                    SplashScreen(
                        onDestination = { destination ->
                            val route = when (destination) {
                                SplashDestination.ONBOARDING -> Routes.Onboarding
                                SplashDestination.LOGIN -> Routes.Login
                                SplashDestination.HOME -> Routes.Home
                            }
                            navController.navigate(route) {
                                popUpTo(Routes.Splash) { inclusive = true }
                            }
                        },
                    )
                }
                composable(Routes.Onboarding) {
                    OnboardingScreen {
                        rootViewModel.completeOnboarding {
                            navController.navigate(Routes.Login) { popUpTo(Routes.Onboarding) { inclusive = true } }
                        }
                    }
                }
                composable(Routes.Login) {
                    LoginScreen(
                        onLoginSuccess = { navController.navigate(Routes.Home) { popUpTo(Routes.Login) { inclusive = true } } },
                        onRegister = { navController.navigate(Routes.Register) },
                        onForgotPassword = {
                            navController.navigate(Routes.ForgotPassword)
                        },
                    )
                }
                composable(Routes.ForgotPassword) {
                    PasswordResetScreen(
                        onBack = navController::popBackStack,
                        onCompleted = {
                            navController.navigate(Routes.Login) {
                                popUpTo(Routes.ForgotPassword) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                    )
                }
                composable(Routes.Register) {
                    RegisterScreen(
                        onBack = navController::popBackStack,
                        onSuccess = { navController.navigate(Routes.ProfileSetup) { popUpTo(Routes.Login) { inclusive = true } } },
                    )
                }
                composable(Routes.ProfileSetup) {
                    ProfileSetupScreen(
                        onDone = {
                            navController.navigate(Routes.Home) {
                                popUpTo(Routes.ProfileSetup) { inclusive = true }
                            }
                        },
                    )
                }
                composable(Routes.Home) {
                    PremiumHomeScreen(
                        onOpenPlan = { navController.navigate("plan/$it") },
                        onOpenPlaces = { navController.navigate(Routes.Places) },
                        onCreateVibe = { navController.navigate("create_vibe?placeId=none") },
                    )
                }
                composable(Routes.Plan, arguments = listOf(navArgument("planId") { type = NavType.StringType })) {
                    PlanResultScreen(
                        onBack = navController::popBackStack,
                        onOpenPlace = { navController.navigate("place/$it") },
                    )
                }
                composable(Routes.Places) {
                    PlacesScreen(onOpenPlace = { navController.navigate("place/$it") }, onOpenMap = { navController.navigate(Routes.Map) })
                }
                composable(Routes.Map) {
                    PlacesMapScreen(
                        onBack = navController::popBackStack,
                        onOpenPlace = { navController.navigate("place/$it") },
                    )
                }
                composable(Routes.Place, arguments = listOf(navArgument("placeId") { type = NavType.StringType })) {
                    PlaceDetailsScreen(
                        onBack = navController::popBackStack,
                        onCreateVibe = { placeId ->
                            navController.navigate("create_vibe?placeId=$placeId")
                        },
                    )
                }
                composable(Routes.Vibes) {
                    VibesScreen(onOpen = { navController.navigate("vibe/$it") }, onCreate = { navController.navigate("create_vibe?placeId=none") })
                }
                composable(Routes.CreateVibe, arguments = listOf(navArgument("placeId") { type = NavType.StringType; defaultValue = "none" })) {
                    CreateVibeScreen(
                        onBack = navController::popBackStack,
                        onCreated = {
                            navController.navigate("vibe/$it") {
                                popUpTo(Routes.Vibes)
                            }
                        },
                    )
                }
                composable(Routes.Vibe, arguments = listOf(navArgument("vibeId") { type = NavType.StringType })) {
                    VibeDetailsScreen(
                        onBack = navController::popBackStack,
                        onChat = { navController.navigate("chat/$it") },
                        onReport = { type, id -> navController.navigate("report/$type/$id") },
                    )
                }
                composable(Routes.Chat, arguments = listOf(navArgument("vibeId") { type = NavType.StringType })) {
                    ChatScreen(
                        onBack = navController::popBackStack,
                        onReport = { type, id ->
                            navController.navigate("report/$type/$id")
                        },
                    )
                }
                composable(Routes.Notifications) {
                    NotificationsScreen(
                        onOpenVibe = { navController.navigate("vibe/$it") },
                    )
                }
                composable(Routes.Profile) {
                    ProfileScreen(
                        onSettings = { navController.navigate(Routes.Settings) },
                        onSafety = { navController.navigate(Routes.Safety) },
                        onMyVibes = { navController.navigate(Routes.Vibes) },
                        onSavedPlaces = { navController.navigate(Routes.Places) },
                    )
                }
                composable(Routes.Settings) {
                    SettingsScreen(
                        onBack = navController::popBackStack,
                        onLoggedOut = { navController.navigate(Routes.Login) { popUpTo(navController.graph.id) { inclusive = true } } },
                    )
                }
                composable(Routes.Safety) {
                    SafetyCenterScreen(
                        onBack = navController::popBackStack,
                        onBlockedUsers = { navController.navigate(Routes.BlockedUsers) },
                    )
                }
                composable(Routes.BlockedUsers) {
                    BlockedUsersScreen(onBack = navController::popBackStack)
                }
                composable(
                    Routes.Report,
                    arguments = listOf(
                        navArgument("targetType") { type = NavType.StringType },
                        navArgument("targetId") { type = NavType.StringType },
                    ),
                ) { ReportScreen(navController::popBackStack) }
            }
        }
    }
}
