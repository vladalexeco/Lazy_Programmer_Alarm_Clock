package ru.vladalexeco.lazyprogrammer.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.AlarmListScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.AlarmTaskScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.CreateTaskScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.SettingsScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.StatisticsScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.TaskSetScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            CreateTaskScreen(
                onSaveButtonClick = {
                    navHostController.navigate(route = Plug.route) {
                        popUpTo(route = Task.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Results.route) {
            StatisticsScreen()
        }

        composable(route = Settings.route) {
            SettingsScreen()
        }

        composable(route = Quest.route) {
            AlarmTaskScreen(
                onCompleteClick = {
                    navHostController.navigate(route = Alarms.route)
                }
            )
        }

        composable(route = Plug.route) {
            TaskSetScreen(
                onButtonClick = {
                    navHostController.navigate(route = Task.route) {
                        popUpTo(route = Plug.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}