package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.modifyMapToString
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.CardColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.Pink80

@Composable
fun RoundButtonWithLetter(
    modifier: Modifier = Modifier,
    letterSymbol: String?,
    size: Dp = 32.dp,
    uncheckedColor: Color = CardColor,
    checkedColor: Color = Pink80,
    isChecked: Boolean,
    onClick: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .border(
                width = 1.dp,
                color = Pink80,
                shape = CircleShape
            )
            .background(
                color = if (isChecked) checkedColor else uncheckedColor,
                shape = CircleShape
            )
            .clickable {
                onClick.invoke()
            }
    ) {
        if (letterSymbol != null) {
            Text(
                text = letterSymbol,
                style = TextStyle(
                    color = if (isChecked) BackgroundColor else MainTextColor
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RoundButtonWithLetterPreview() {

    var isChecked by remember {
        mutableStateOf(true)
    }

    RoundButtonWithLetter(
        letterSymbol = "В",
        isChecked = isChecked,
        onClick = { isChecked = !isChecked }
    )
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun WeekDaysRow(
    modifier: Modifier = Modifier,
    letters: Array<String?> = arrayOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"),
    isCheckedOne: Boolean,
    isCheckedTwo: Boolean,
    isCheckedThree: Boolean,
    isCheckedFour: Boolean,
    isCheckedFive: Boolean,
    isCheckedSix: Boolean,
    isCheckedSeven: Boolean,
    onClickOne: () -> Unit,
    onClickTwo: () -> Unit,
    onClickThree: () -> Unit,
    onClickFour: () -> Unit,
    onClickFive: () -> Unit,
    onClickSix: () -> Unit,
    onClickSeven: () -> Unit,
    onDaysArrayChange: (Map<String, Boolean>) -> Unit
) {
    val letterMap = linkedMapOf(
        "Пн" to isCheckedOne,
        "Вт" to isCheckedTwo,
        "Ср" to isCheckedThree,
        "Чт" to isCheckedFour,
        "Пт" to isCheckedFive,
        "Сб" to isCheckedSix,
        "Вс" to isCheckedSeven
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundButtonWithLetter(
            letterSymbol = letters[0],
            isChecked = isCheckedOne,
            onClick = {
                onClickOne.invoke()
                letterMap["Пн"] = !letterMap["Пн"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[1],
            isChecked = isCheckedTwo,
            onClick = {
                onClickTwo.invoke()
                letterMap["Вт"] = !letterMap["Вт"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[2],
            isChecked = isCheckedThree,
            onClick = {
                onClickThree.invoke()
                letterMap["Ср"] = !letterMap["Ср"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[3],
            isChecked = isCheckedFour,
            onClick = {
                onClickFour.invoke()
                letterMap["Чт"] = !letterMap["Чт"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[4],
            isChecked = isCheckedFive,
            onClick = {
                onClickFive.invoke()
                letterMap["Пт"] = !letterMap["Пт"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[5],
            isChecked = isCheckedSix,
            onClick = {
                onClickSix.invoke()
                letterMap["Сб"] = !letterMap["Сб"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[6],
            isChecked = isCheckedSeven,
            onClick = {
                onClickSeven.invoke()
                letterMap["Вс"] = !letterMap["Вс"]!!
                onDaysArrayChange.invoke(letterMap)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WeekDaysRowPreview() {

    var isCheckedOne by remember { mutableStateOf(true) }
    var isCheckedTwo by remember { mutableStateOf(true) }
    var isCheckedThree by remember { mutableStateOf(true) }
    var isCheckedFour by remember { mutableStateOf(true) }
    var isCheckedFive by remember { mutableStateOf(true) }
    var isCheckedSix by remember { mutableStateOf(true) }
    var isCheckedSeven by remember { mutableStateOf(true) }

    WeekDaysRow(
        isCheckedOne = isCheckedOne,
        isCheckedTwo = isCheckedTwo,
        isCheckedThree = isCheckedThree,
        isCheckedFour = isCheckedFour,
        isCheckedFive = isCheckedFive,
        isCheckedSix = isCheckedSix,
        isCheckedSeven = isCheckedSeven,
        onClickOne = { isCheckedOne = !isCheckedOne },
        onClickTwo = { isCheckedTwo = !isCheckedTwo },
        onClickThree = { isCheckedThree = !isCheckedThree },
        onClickFour = { isCheckedFour = !isCheckedFour },
        onClickFive = { isCheckedFive = !isCheckedFive },
        onClickSix = { isCheckedSix = !isCheckedSix },
        onClickSeven = { isCheckedSeven = !isCheckedSeven },
        onDaysArrayChange = { letterMap ->
            Log.i("DAYS", letterMap.toString())
        }
    )
}