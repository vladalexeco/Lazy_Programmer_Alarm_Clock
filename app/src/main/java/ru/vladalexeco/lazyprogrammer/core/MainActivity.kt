package ru.vladalexeco.lazyprogrammer.core

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vladalexeco.lazyprogrammer.core.navigation.Alarms
import ru.vladalexeco.lazyprogrammer.core.navigation.AppNavScreen
import ru.vladalexeco.lazyprogrammer.core.navigation.Quest
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LazyProgrammerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            LazyProgrammerTheme {

                val navController = rememberNavController()

                val navigateTo = intent.getStringExtra("navigateTo")

                LaunchedEffect(navigateTo) {
                    if (navigateTo == "alarm") {
                        navController.navigate(route = Quest.route)
                    }
                }

                AppNavScreen(
                    navController = navController,
                    startDestination = Alarms.route
                )
            }
        }
    }
}