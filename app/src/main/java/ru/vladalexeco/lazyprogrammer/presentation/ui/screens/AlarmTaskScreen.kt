package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.buildColoredString
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenSideEffect
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenState
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.CardColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.WrongAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.ButtonChoiceRow
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.SimpleButton
import ru.vladalexeco.lazyprogrammer.presentation.viewmodel.AlarmTaskScreenViewModel

@Composable
fun AlarmTaskScreen(
    onCompleteClick: () -> Unit
) {
    val viewModel: AlarmTaskScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                AlarmTaskScreenSideEffect.GoToAnotherScreen -> {
                    onCompleteClick.invoke()
                }
            }
        }
    }

    AlarmTaskScreen(
        state = state,
        onEvent = { alarmTaskScreenEvent ->
            viewModel.onEvent(alarmTaskScreenEvent)
        }
    )
}

@Composable
fun AlarmTaskScreen(
    state: AlarmTaskScreenState,
    onEvent: (AlarmTaskScreenEvent) -> Unit
) {
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
                text = "Задание ${state.taskNumber} из ${state.totalNumberOfTasks}" ,
                style = TextStyle(color = MainTextColor, fontSize = 24.sp)
            )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.menu_dots_vertical),
                colorFilter = ColorFilter.tint(MainTextColor),
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Язык: ${state.language};",
                style = TextStyle(color = AccentColor, fontSize = 20.sp)
            )

            Text(
                text = "Сложность: ${state.complexity}",
                style = TextStyle(color = AccentColor, fontSize = 20.sp)
            )
        }


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            text = state.quest,
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
                text = state.code,
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
            options = state.choiceOptions,
            rightAnswerIndex = state.rightAnswer - 1,
            answerIsSelected = state.answerIsSelected,
            onButtonClick = { isCorrectAnswer ->
                if (state.answerIsSelected) {
                    onEvent.invoke(AlarmTaskScreenEvent.ProcessUserTaskResponse(isCorrectAnswer))
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        SimpleButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            text = if (state.answerIsSelected) "Продолжить" else "Пропустить",
            backgroundColor = if (state.answerIsSelected) RightAnswerColor else LightTextColor,
            textColor = MainTextColor,
            onClick = {
                onEvent.invoke(AlarmTaskScreenEvent.RenderNewTaskOrCompleteSession)
            }
        )

        SimpleButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            text = "Завершить",
            backgroundColor = WrongAnswerColor,
            textColor = MainTextColor,
            onClick = {
                onEvent.invoke(AlarmTaskScreenEvent.ProcessWithEmergencyCompletionWithTask)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AlarmTaskScreenPreview() {
    AlarmTaskScreen(
        state = AlarmTaskScreenState(),
        onEvent = {}
    )
}