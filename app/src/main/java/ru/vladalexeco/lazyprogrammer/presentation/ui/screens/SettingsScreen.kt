package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.presentation.state.SettingsScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.SettingsScreenState
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen.CheckBoxColumn
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen.EstimateRangeSlider
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen.EstimateSlider
import ru.vladalexeco.lazyprogrammer.presentation.viewmodel.SettingsScreenViewModel

@Composable
fun SettingsScreen() {

    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    SettingsScreen(
        state = state,
        onEvent = { settingsScreenEvent ->
            viewModel.onEvent(settingsScreenEvent)
        }
    )
}

@Composable
fun SettingsScreen(
    state: SettingsScreenState,
    onEvent: (SettingsScreenEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp),
            text = "Настройки",
            style = TextStyle(color = MainTextColor, fontSize = 24.sp)
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp),
            text = "Языки программирования",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        CheckBoxColumn(
            modifier = Modifier.fillMaxWidth(),
            listOfLabels = supportedProgrammingLanguages,
            initialValues = state.languageMap,
            onCheckBoxClick = { key, isChecked ->
                onEvent.invoke(SettingsScreenEvent.ChangeValueOnLanguageMap(
                    key = key,
                    value = isChecked
                ))
            }
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp),
            text = "Интервал сложности",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        EstimateRangeSlider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            initialStart = state.complexityStart.toInt(),
            initialEnd = state.complexityEnd.toInt(),
            onRangeChanged = { startValue, endValue ->
                onEvent(SettingsScreenEvent.ChangeComplexityValueRange(
                    start = startValue,
                    end = endValue
                ))
            }
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Сложность заданий от ${state.complexityStart} до ${state.complexityEnd} баллов",
            style = TextStyle(color = MainTextColor, fontSize = 16.sp)
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp),
            text = "Количество заданий",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        EstimateSlider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            start = state.numberOfTasks.toInt(),
            onValueChange = { newValue ->
                onEvent(SettingsScreenEvent.ChangeNumberOfTasksValue(value = newValue))
            }
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Количество заданий за одну сессию - ${state.numberOfTasks}",
            style = TextStyle(color = MainTextColor, fontSize = 16.sp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    SettingsScreen(
        state = SettingsScreenState(),
        onEvent = {}
    )
}