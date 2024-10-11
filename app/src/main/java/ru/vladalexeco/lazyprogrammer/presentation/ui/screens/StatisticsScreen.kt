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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.WrongAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.statistics_screen.DataColumn

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier
) {
    val dataMap: Map<String, Int> = linkedMapOf(
        "kotlin" to 7,
        "java" to 11,
        "python" to 9
    )

    val currentUserStatus = 1

    val statusMap: Map<Int, String> = mapOf(
        1 to "Новичек",
        2 to "Любитель",
        3 to "Опытный",
        4 to "Мастер",
        5 to "Профессионал"
    )

    val emojiMap: Map<Int, Int> = mapOf(
        1 to R.drawable.newcomer,
        2 to R.drawable.fan,
        3 to R.drawable.skilled,
        4 to R.drawable.master,
        5 to R.drawable.professional
    )

    val currentEmoji: Int = emojiMap[currentUserStatus] ?: R.drawable.newcomer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
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
                text = "20",
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
                text = "56",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            text = "Из них решенных:",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        DataColumn(
            dataMap = dataMap
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
                text = "17",
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
            text = statusMap[currentUserStatus] ?: "Неизвестный статус",
            style = TextStyle(color = AccentColor, fontSize = 24.sp)
        )

        Image(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(72.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(currentEmoji),
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun StatisticsScreenPreview() {
    StatisticsScreen()
}