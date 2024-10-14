package ru.vladalexeco.lazyprogrammer.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.bottom_navigation_bar.CustomBottomBar

@Composable
fun AppNavScreen(
    startDestination: String
) {
    val screensWithBottomBar = listOf(Alarms.route, Task.route, Results.route, Settings.route)

    val navHostController = rememberNavController()

    var customBottomBarHeight by remember { mutableStateOf(0.dp) }

    Scaffold(
        bottomBar = {
            if (screensWithBottomBar.contains(currentRoute(navController = navHostController))) {
                Box(
                    Modifier.onSizeChanged { size ->
                        customBottomBarHeight = size.height.dp
                    }
                ) {
                    BottomBar(navHostController = navHostController)
                }
            }
        }
    ) {
        AppNavHost(
            navHostController = navHostController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(it)
                .padding(bottom = customBottomBarHeight)
        )
    }
}

@Composable
@Preview
fun AppNavScreenPreview() {
    AppNavScreen(
        startDestination = Alarms.route
    )
}

@Composable
fun BottomBar(
    navHostController: NavHostController
) {
    CustomBottomBar(
        screenRoute1 = Alarms.route,
        screenRoute2 = Task.route,
        screenRoute3 = Results.route,
        screenRoute4 = Settings.route,
        currentRoute = currentRoute(navController = navHostController),
        firstItemClick = {
            navHostController.navigate(Alarms.route) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        secondItemClick = {
            navHostController.navigate(Task.route) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        thirdItemClick = {
            navHostController.navigate(Results.route) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        fourthItemClick = {
            navHostController.navigate(Settings.route) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}