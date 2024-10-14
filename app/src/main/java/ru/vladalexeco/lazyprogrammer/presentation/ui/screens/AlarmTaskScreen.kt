package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.buildColoredString
import ru.vladalexeco.lazyprogrammer.domain.AlarmTask
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.CardColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.WrongAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.ButtonChoice
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.ButtonChoiceRow
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.SimpleButton

@Composable
fun AlarmTaskScreen() {
    // TODO это мок объект. В дальнейшем надо заменить его на объект, который будет приходить
    // TODO из базы данных (удаленной или локальной) при помощи вьюмодели
    val alarmTask = AlarmTask(
        id = 0,
        quest = "Дано начальное значение переменной m. Какое значение m выведет" +
        " функция println()?",
        code = "val str = \"String\"\nvar m = 0 \nvar count = 3 \n" +
        "while (count > 0) {\n    m++ \n    count-- \n} \n" +
        "println(m)",
        choiceOptions = listOf("1", "2", "3" ,"4"),
        rightAnswer = 2,
        language = "kotlin",
        complexity = 1
    )

    val taskNumber = 1 // TODO значение приходит из viewmodel

    val annotatedCode = buildColoredString(
        language = "kotlin",
        codeText = alarmTask.code
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 12.dp)
        ) {
            Text(
                text = "Задание $taskNumber",
                style = TextStyle(color = MainTextColor, fontSize = 24.sp)
            )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.menu_dots_vertical),
                colorFilter = ColorFilter.tint(MainTextColor),
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp
            ),
            text = "Сложность: ${alarmTask.complexity}",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            text = alarmTask.quest,
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .background(color = CardColor, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = AccentColor,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            BasicText(
                modifier = Modifier
                    .scrollable(
                        orientation = Orientation.Vertical,
                        enabled = true,
                        state = rememberScrollState()
                    )
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                text = annotatedCode,
                maxLines = 14,
                style = TextStyle(
                    color = MainTextColor,
                    fontSize = 20.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = "Варианты ответов:",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        ButtonChoiceRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            options = listOf("1", "2", "3", "4"),
            rightAnswerIndex = 2,
            onButtonClick = { isCorrectAnswer ->
                // TODO Действия в случае правильного или неправильного ответа
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        SimpleButton(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            text = "Пропустить",
            backgroundColor = RightAnswerColor,
            textColor = MainTextColor,
            onClick = {}
        )

        SimpleButton(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
            text = "Завершить",
            backgroundColor = WrongAnswerColor,
            textColor = MainTextColor,
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AlarmTaskScreenPreview() {
    AlarmTaskScreen()
}