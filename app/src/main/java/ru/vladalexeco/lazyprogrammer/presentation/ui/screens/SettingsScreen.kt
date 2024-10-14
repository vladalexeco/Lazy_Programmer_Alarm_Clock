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
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen.CheckBoxColumn
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen.EstimateRangeSlider
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen.EstimateSlider

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val languageList = supportedProgrammingLanguages
    val initialValues: MutableMap<String, Boolean> = remember { mutableStateMapOf() }

    languageList.forEach { item -> initialValues[item] = false }

    var complexityStart by remember { mutableIntStateOf(1) }
    var complexityEnd by remember { mutableIntStateOf(5) }

    var numberOfTasks by remember { mutableIntStateOf(3) }

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
            listOfLabels = languageList,
            initialValues = initialValues,
            onCheckBoxClick = { key, isChecked ->
                initialValues[key] = isChecked
            }
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp),
            text = "Интервал сложности",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        EstimateRangeSlider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            initialStart = complexityStart,
            initialEnd = complexityEnd,
            onRangeChanged = { startValue, endValue ->
                complexityStart = startValue
                complexityEnd = endValue
            },
            onRangeChangeFinish = { startValue, endValue ->

            }
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Сложность заданий от $complexityStart до $complexityEnd баллов",
            style = TextStyle(color = MainTextColor, fontSize = 16.sp)
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp),
            text = "Количество заданий",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        EstimateSlider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            start = numberOfTasks,
            onValueChange = { newValue ->
                numberOfTasks = newValue
            },
            onValueChangeFinish = { newValue ->

            }
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Количество заданий за одну сессию - $numberOfTasks",
            style = TextStyle(color = MainTextColor, fontSize = 16.sp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    SettingsScreen()
}