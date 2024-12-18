package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.SimpleButton

@Composable
fun TaskSetScreen(
    onButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Задание успешно сохранено",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            SimpleButton(
                modifier = Modifier.fillMaxWidth().padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 16.dp
                ),
                backgroundColor = RightAnswerColor,
                textColor = MainTextColor,
                text = "Вернуться на экранн задания",
                onClick = { onButtonClick.invoke() }
            )
        }
    }
}

@Composable
@Preview
fun TaskSetScreenPreview() {
    TaskSetScreen(
        onButtonClick = {}
    )
}
