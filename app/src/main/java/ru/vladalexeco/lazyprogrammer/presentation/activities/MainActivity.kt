package ru.vladalexeco.lazyprogrammer.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.vladalexeco.lazyprogrammer.core.navigation.Alarms
import ru.vladalexeco.lazyprogrammer.core.navigation.AppNavScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.screens.CreateTaskScreen
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LazyProgrammerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyProgrammerTheme {
                AppNavScreen(
                    startDestination = Alarms.route
                )
            }
        }
    }
}