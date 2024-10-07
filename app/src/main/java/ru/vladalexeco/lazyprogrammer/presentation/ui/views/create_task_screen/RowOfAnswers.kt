package ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RowOfAnswers(
    modifier: Modifier = Modifier,
    numberOfAnswers: Int,
    onValueChange: (Int, String) -> Unit
) {

    var answers by remember { mutableStateOf(List(numberOfAnswers) { "" }) }

    LaunchedEffect(numberOfAnswers) {
        answers = List(numberOfAnswers) { "" }
    }

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
                    answers = answers.toMutableList().also { it[pos] = value }
                    onValueChange.invoke(pos, value)
                },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun RowOfAnswersPreview() {
    RowOfAnswers(
        numberOfAnswers = 4,
        onValueChange = { _, _ ->

        }
    )
}