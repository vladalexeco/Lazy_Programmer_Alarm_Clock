package ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RowOfAnswers(
    modifier: Modifier = Modifier,
    numberOfAnswers: Int,
    onValueChange: (Int, String) -> Unit
) {
    val listOfIndexes = (0 until numberOfAnswers).toList()

    LaunchedEffect(numberOfAnswers) {
        // Reset textValue state when numberOfAnswers changes
        listOfIndexes.forEach { index ->
            onValueChange.invoke(index, "")
        }
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(listOfIndexes) { index ->
            CoreTextField(
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
    RowOfAnswers(
        numberOfAnswers = 4,
        onValueChange = { _, _ ->

        }
    )
}