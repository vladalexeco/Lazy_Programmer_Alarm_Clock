package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.WrongAnswerColor

@Composable
fun ButtonChoiceRow(
    modifier: Modifier = Modifier,
    options: List<String>,
    answerIsSelected: Boolean,
    isReset: Boolean,
    rightAnswerIndex: Int,
    onButtonClick: (Boolean) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        itemsIndexed(options) { index, key ->
            ButtonChoice(
                defaultColor = LightTextColor,
                rightAnswerColor = RightAnswerColor,
                wrongAnswerColor = WrongAnswerColor,
                width = 64.dp,
                height = 32.dp,
                isReset = isReset,
                text = key,
                isRightAnswer = index == rightAnswerIndex,
                answerIsSelected = answerIsSelected,
                onClick = {
                    onButtonClick.invoke(index == rightAnswerIndex)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonChoiceRowPreview() {

    var answerIsSelected by remember { mutableStateOf(false) }
    var isReset by remember { mutableStateOf(false) }

    Column {
        ButtonChoiceRow(
            options = listOf("1", "2", "3"),
            rightAnswerIndex = 2,
            answerIsSelected = answerIsSelected,
            isReset = isReset,
            onButtonClick = { isCorrectAnswer ->
                answerIsSelected = true
                isReset = false
            }
        )

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                answerIsSelected = false
                isReset = true
            }
        ) {
            Text(text = "Reset")
        }
    }
}

@Composable
fun ButtonChoice(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    defaultColor: Color,
    wrongAnswerColor: Color,
    rightAnswerColor: Color,
    answerIsSelected: Boolean,
    isReset: Boolean,
    width: Dp,
    height: Dp,
    text: String,
    isRightAnswer: Boolean
) {

    var currentButtonColor by remember {
        mutableStateOf(defaultColor)
    }

    var currentTextColor by remember {
        mutableStateOf(DialogBoxColor)
    }

    LaunchedEffect(isReset) {
        if (isReset) {
            currentButtonColor = defaultColor
            currentTextColor = DialogBoxColor
        }
    }

    Box(
        modifier = modifier
            .size(width = width, height = height)
            .background(
                color = currentButtonColor,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable {
                if (!answerIsSelected) {
                    currentButtonColor = if (isRightAnswer) rightAnswerColor else wrongAnswerColor
                    currentTextColor = MainTextColor
                    onClick.invoke()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = TextStyle(color = currentTextColor))
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonChoicePreview() {
    ButtonChoice(
        defaultColor = LightTextColor,
        rightAnswerColor = RightAnswerColor,
        wrongAnswerColor = WrongAnswerColor,
        width = 64.dp,
        height = 32.dp,
        text = "1",
        answerIsSelected = false,
        isReset = false,
        onClick = {},
        isRightAnswer = false
    )
}