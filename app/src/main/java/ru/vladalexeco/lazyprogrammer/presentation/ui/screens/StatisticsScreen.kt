package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vladalexeco.lazyprogrammer.presentation.state.StatisticsScreenState
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.WrongAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.statistics_screen.DataColumn
import ru.vladalexeco.lazyprogrammer.presentation.viewmodel.StatisticsScreenViewModel

@Composable
fun StatisticsScreen() {

    val viewModel: StatisticsScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    StatisticsScreen(state = state)
}


@Composable
fun StatisticsScreen(
    state: StatisticsScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp),
            text = "Статистика",
            style = TextStyle(color = MainTextColor, fontSize = 24.sp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp,
                end = 16.dp,
                top = 32.dp,
                bottom = 16.dp
            )
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Всего сессий",
                style = TextStyle(color = AccentColor, fontSize = 20.sp)
            )

            Text(
                text = state.numberOfSessions.toString(),
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Всего заданий",
                style = TextStyle(color = AccentColor, fontSize = 20.sp)
            )

            Text(
                text = state.numberOfTasks.toString(),
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            text = "Из них решенных:",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        DataColumn(
            dataMap = state.languageResults
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp,
                end = 16.dp,
                top = 32.dp
            )
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Допущено ошибок",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            Text(
                text = state.numberOfMistakes.toString(),
                style = TextStyle(color = WrongAnswerColor, fontSize = 20.sp)
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 64.dp)
                .align(Alignment.CenterHorizontally),
            text = "Статус",
            style = TextStyle(color = MainTextColor, fontSize = 24.sp)
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = state.status,
            style = TextStyle(color = AccentColor, fontSize = 24.sp)
        )

        Image(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(72.dp)
                .align(Alignment.CenterHorizontally),
            painter = state.emojiStatus.painter(),
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun StatisticsScreenPreview() {
    StatisticsScreen(state = StatisticsScreenState())
}