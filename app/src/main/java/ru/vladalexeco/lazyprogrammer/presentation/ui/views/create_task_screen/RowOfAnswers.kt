package ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RowOfAnswers(
    modifier: Modifier = Modifier,
    answers: List<String>,
    onValueChange: (Int, String) -> Unit
) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(answers.size) { index ->
            CoreTextField(
                textValue = answers[index],
                hint = "",
                index = index,
                onValueChange = { pos, value ->
                    onValueChange.invoke(pos, value)
                },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun RowOfAnswersPreview() {

    val answerValues = remember { mutableStateListOf("", "", "", "") }

    RowOfAnswers(
        answers = answerValues,
        onValueChange = { pos, value ->
            answerValues[pos] = value
        }
    )
}