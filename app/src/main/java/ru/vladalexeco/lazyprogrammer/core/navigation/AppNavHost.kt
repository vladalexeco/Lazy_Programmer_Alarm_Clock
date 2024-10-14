package ru.vladalexeco.lazyprogrammer.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.AlarmListScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.CreateTaskScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.SettingsScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.StatisticsScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    startDestination: String,
    modifier: Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Alarms.route) {
            AlarmListScreen()
        }

        composable(route = Task.route) {
            CreateTaskScreen()
        }

        composable(route = Results.route) {
            StatisticsScreen()
        }

        composable(route = Settings.route) {
            SettingsScreen()
        }
    }
}