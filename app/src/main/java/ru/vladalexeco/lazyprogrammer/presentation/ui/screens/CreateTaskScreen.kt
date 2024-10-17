package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_MAX
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.SimpleButton
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen.DropdownList
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen.RowOfAnswers

@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier
) {
    val languageList = supportedProgrammingLanguages
    val complexityValueList = List(COMPLEXITY_MAX) { (it + 1).toString() }
    val numberOfAnswersList = listOf("2", "3", "4", "5")

    var numberOfAnswers by remember { mutableIntStateOf(4) }
    var taskCode by remember { mutableStateOf("") }
    var taskQuestion by remember { mutableStateOf("") }
    var answerOptions by remember { mutableStateOf(List(numberOfAnswers) { (it + 1).toString() }) }
    var answerOptionsCurrentValue by remember { mutableStateOf("") }

    var answersArr: Array<String?> = arrayOfNulls(numberOfAnswers)

    LaunchedEffect(numberOfAnswers) {
        answerOptions = List(numberOfAnswers) { (it + 1).toString() }
    }

    Column(
        modifier = modifier
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
                items = languageList,
                hint = "kotlin",
                onItemSelect = { languageValue ->

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
                items = complexityValueList,
                hint = "1 - 10",
                onItemSelect = {}
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
            value = taskQuestion,
            textStyle = TextStyle(color = MainTextColor, fontSize = 16.sp),
            onValueChange = { newValue ->
                taskQuestion = newValue
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
            value = taskCode,
            textStyle = TextStyle(color = MainTextColor, fontSize = 16.sp),
            onValueChange = { newValue ->
                taskCode = newValue
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp,)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "Количество ответов",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            DropdownList(
                modifier = Modifier.align(Alignment.CenterEnd),
                items = numberOfAnswersList,
                value = numberOfAnswers.toString(),
                hint = "",
                onItemSelect = { newNumberOfAnswers ->
                    answersArr = arrayOfNulls(newNumberOfAnswers.toInt())
                    numberOfAnswers = newNumberOfAnswers.toInt()
                    answerOptionsCurrentValue = ""
                }
            )
        }

        if (numberOfAnswers != 0) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                text = "Варианты ответов",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            RowOfAnswers(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                numberOfAnswers = numberOfAnswers,
                onValueChange = { index, value ->
                    answersArr[index] = value
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Вариант правильного ответа",
                style = TextStyle(color = MainTextColor, fontSize = 20.sp)
            )

            DropdownList(
                items = answerOptions,
                value = answerOptionsCurrentValue,
                hint = "",
                onItemSelect = { newValue ->
                    answerOptionsCurrentValue = newValue
                }
            )
        }

        SimpleButton(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp,
                end = 16.dp,
                top = 32.dp,
                bottom = 16.dp
            ),
            text = "Создать задание",
            backgroundColor = RightAnswerColor,
            textColor = MainTextColor,
            onClick = {}
        )

        Spacer(
            modifier = Modifier.padding(bottom = 84.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CreateTaskScreenPreview() {
    CreateTaskScreen()
}