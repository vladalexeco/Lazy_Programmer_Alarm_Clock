package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor

@Composable
fun ButtonChoiceRow(
    modifier: Modifier = Modifier,
    options: List<String>,
    rightAnswerIndex: Int
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        itemsIndexed(options) { index, option ->
            ButtonChoice(
                color = LightTextColor,
                width = 64.dp,
                height = 32.dp,
                text = option,
                onClick = {
                    if (index == rightAnswerIndex) {

                    } else {

                    }
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonChoiceRowPreview() {
    ButtonChoiceRow(
        options = listOf("1", "2", "3", "4"),
        rightAnswerIndex = 2
    )
}

@Composable
fun ButtonChoice(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color,
    width: Dp,
    height: Dp,
    text: String
) {
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .background(color = color, shape = RoundedCornerShape(6.dp))
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonChoicePreview() {
    ButtonChoice(
        color = LightTextColor,
        width = 64.dp,
        height = 32.dp,
        text = "1",
        onClick = {}
    )
}