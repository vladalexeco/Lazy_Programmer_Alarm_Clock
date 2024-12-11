package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_MAX
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.UNDEFINED_VALUE
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.generateUniqueId
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenSideEffect
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenState
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.SimpleButton
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen.DropdownList
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen.RowOfAnswers
import ru.vladalexeco.lazyprogrammer.presentation.viewmodel.CreateTaskScreenViewModel

@OptIn(FlowPreview::class)
@Composable
fun CreateTaskScreen() {

    val viewModel: CreateTaskScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CreateTaskScreenSideEffect.ShowMessage -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(state.taskQuestion) {
        snapshotFlow { state.taskQuestion }
            .debounce(1500)
            .collect { question ->
                viewModel.onEvent(CreateTaskScreenEvent.SaveQuestionValueInSharedPreferences(question))
            }
    }

    LaunchedEffect(state.taskCode) {
        snapshotFlow { state.taskCode }
            .debounce(1500)
            .collect { code ->
                viewModel.onEvent(CreateTaskScreenEvent.SaveCodeValueInSharedPreferences(code))
            }
    }

    CreateTaskScreen(
        state = state,
        onEvent = { createTaskScreenEvent ->
            viewModel.onEvent(createTaskScreenEvent)
        }
    )
}

@Composable
fun CreateTaskScreen(
    state: CreateTaskScreenState,
    onEvent: (CreateTaskScreenEvent) -> Unit
) {
    val languageList = supportedProgrammingLanguages
    val complexityValueList = List(COMPLEXITY_MAX) { (it + 1).toString() }
    val numberOfAnswersList = listOf("2", "3", "4", "5")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp),
            text = "Новое задание",
            style = TextStyle(color = MainTextColor, fontSize = 24.sp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "Язык",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            DropdownList(
                modifier = Modifier.align(Alignment.CenterEnd),
                value = state.language,
                items = languageList,
                hint = "kotlin",
                onItemSelect = { languageValue ->
                    onEvent.invoke(CreateTaskScreenEvent.SaveLanguageData(languageValue))
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "Сложность",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            DropdownList(
                modifier = Modifier.align(Alignment.CenterEnd),
                value = state.complexity,
                items = complexityValueList,
                hint = "1 - 10",
                onItemSelect = { newComplexityValue ->
                    onEvent.invoke(CreateTaskScreenEvent.SaveComplexityData(newComplexityValue))
                }
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            text = "Вопрос задания",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(140.dp)
                .border(
                    width = 1.dp,
                    color = AccentColor,
                    shape = RoundedCornerShape(6.dp)
                ),
            value = state.taskQuestion,
            textStyle = TextStyle(color = MainTextColor, fontSize = 16.sp),
            onValueChange = { newTaskQuestionValue ->
                onEvent.invoke(CreateTaskScreenEvent.SaveTaskQuestionData(newTaskQuestionValue))
            }
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            text = "Окно ввода задания",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(320.dp)
                .border(
                    width = 1.dp,
                    color = AccentColor,
                    shape = RoundedCornerShape(6.dp)
                ),
            value = state.taskCode,
            textStyle = TextStyle(color = MainTextColor, fontSize = 16.sp),
            onValueChange = { newTaskCodeValue ->
                onEvent.invoke(CreateTaskScreenEvent.SaveTaskCodeData(newTaskCodeValue))
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "Количество ответов",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            DropdownList(
                modifier = Modifier.align(Alignment.CenterEnd),
                items = numberOfAnswersList,
                value = state.numberOfAnswers.toString(),
                hint = "",
                onItemSelect = { newNumberOfAnswers ->
                    onEvent.invoke(
                        CreateTaskScreenEvent.SaveNumberOfAnswersData(newNumberOfAnswers)
                    )
                }
            )
        }


        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Варианты ответов",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        RowOfAnswers(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            answers = state.answersList,
            onValueChange = { index, value ->
                val currentAnswersList = state.answersList.toMutableList()
                currentAnswersList[index] = value

                onEvent.invoke(
                    CreateTaskScreenEvent.SaveNumberOfAnswersList(currentAnswersList.toList())
                )
            }
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Вариант правильного ответа",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            DropdownList(
                items = state.answerOptions,
                value = state.answerOptionsCurrentValue,
                hint = "",
                onItemSelect = { newAnswerOptionValue ->
                    onEvent.invoke(
                        CreateTaskScreenEvent.SaveAnswerOptionsCurrentValueData(
                            newAnswerOptionValue
                        )
                    )
                }
            )
        }

        SimpleButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 32.dp,
                    bottom = 8.dp
                ),
            text = "Создать задание",
            backgroundColor = RightAnswerColor,
            textColor = MainTextColor,
            onClick = {

                val newAlarmTask = AlarmTask(
                    id = generateUniqueId(),
                    quest = state.taskQuestion,
                    code = state.taskCode,
                    choiceOptions = state.answersList,
                    rightAnswer = if (state.answerOptionsCurrentValue.isEmpty()) UNDEFINED_VALUE else
                        state.answerOptionsCurrentValue.toInt(),
                    language = state.language,
                    complexity = if (state.complexity.isEmpty()) UNDEFINED_VALUE else
                        state.complexity.toInt()
                )

                onEvent.invoke(CreateTaskScreenEvent.SaveAlarmTaskToDatabase(newAlarmTask))
            }
        )

        SimpleButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            text = "Очистить все поля",
            backgroundColor = AccentColor,
            textColor = MainTextColor,
            onClick = {
                onEvent.invoke(CreateTaskScreenEvent.ResetAllFieldsToTheirDefaultValues)
            }
        )

        Spacer(
            modifier = Modifier.padding(bottom = 88.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CreateTaskScreenPreview() {
    CreateTaskScreen(
        state = CreateTaskScreenState(),
        onEvent = {}
    )
}