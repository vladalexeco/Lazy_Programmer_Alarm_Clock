package ru.vladalexeco.lazyprogrammer.presentation.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.CardColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxTextFieldColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.Pink80
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.SubTextColor

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    icon: Painter = painterResource(id = R.drawable.plus),
    backgroundColor: Color = AccentColor,
    iconTint: Color = BackgroundColor,
    size: Dp = 64.dp,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(color = backgroundColor, shape = CircleShape)
            .clickable {
                onClick.invoke()
            }
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconTint),
            modifier = Modifier.size(size / 2)
        )
    }
}

@Composable
fun RoundButtonWithLetter(
    modifier: Modifier = Modifier,
    letterSymbol: String,
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
        Text(
            text = letterSymbol,
            style = TextStyle(
                color = if (isChecked) BackgroundColor else MainTextColor
            )
        )
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

@Composable
fun RoundButtonRow(
    modifier: Modifier = Modifier,
    letters: Array<String> = arrayOf("П", "В", "С", "Ч", "П", "С", "В"),
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
    onArrayResult: (Array<Boolean>) -> Unit
) {

    val valueArray = arrayOf(
        isCheckedOne,
        isCheckedTwo,
        isCheckedThree,
        isCheckedFour,
        isCheckedFive,
        isCheckedSix,
        isCheckedSeven
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
                valueArray[0] = !valueArray[0]
                onArrayResult.invoke(valueArray)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[1],
            isChecked = isCheckedTwo,
            onClick = {
                onClickTwo.invoke()
                valueArray[1] = !valueArray[1]
                onArrayResult.invoke(valueArray)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[2],
            isChecked = isCheckedThree,
            onClick = {
                onClickThree.invoke()
                valueArray[2] = !valueArray[2]
                onArrayResult.invoke(valueArray)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[3],
            isChecked = isCheckedFour,
            onClick = {
                onClickFour.invoke()
                valueArray[3] = !valueArray[3]
                onArrayResult.invoke(valueArray)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[4],
            isChecked = isCheckedFive,
            onClick = {
                onClickFive.invoke()
                valueArray[4] = !valueArray[4]
                onArrayResult.invoke(valueArray)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[5],
            isChecked = isCheckedSix,
            onClick = {
                onClickSix.invoke()
                valueArray[5] = !valueArray[5]
                onArrayResult.invoke(valueArray)
            }
        )
        RoundButtonWithLetter(
            letterSymbol = letters[6],
            isChecked = isCheckedSeven,
            onClick = {
                onClickSeven.invoke()
                valueArray[6] = !valueArray[6]
                onArrayResult.invoke(valueArray)
            }
        )

    }
}

@Composable
@Preview(showBackground = true)
fun RoundButtonRowPreview() {

    var isCheckedOne by remember { mutableStateOf(true) }
    var isCheckedTwo by remember { mutableStateOf(true) }
    var isCheckedThree by remember { mutableStateOf(true) }
    var isCheckedFour by remember { mutableStateOf(true) }
    var isCheckedFive by remember { mutableStateOf(true) }
    var isCheckedSix by remember { mutableStateOf(true) }
    var isCheckedSeven by remember { mutableStateOf(true) }

    var daysArray: Array<Boolean>

    RoundButtonRow(
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
        onArrayResult = { newDaysArray ->
            daysArray = newDaysArray
            Log.i("DAYS", daysArray.toList().toString())
        }
    )
}

@Composable
fun DialogBox(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    roundCorner: Dp,
    backgroundColor: Color,
    hourValue: String,
    minuteValue: String,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String ) -> Unit,
    isHourFocused: Boolean,
    isMinuteFocused: Boolean,
    onHourFocusChange: (Boolean) -> Unit,
    onMinuteFocusChange: (Boolean) -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(color = backgroundColor, shape = RoundedCornerShape(roundCorner))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                text = "Выбор времени",
                style = TextStyle(color = MainTextColor, fontSize = 16.sp)
            )

            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TimeTextField(
                    isBorderVisible = isHourFocused,
                    textFieldValue = hourValue,
                    onTextFieldValueChange = { newValue ->
                        onHourChange.invoke(newValue)
                    },
                    onFocusChanged = { isFocused ->
                        onHourFocusChange.invoke(isFocused)
                    }
                )
                Text(
                    text = ":",
                    style = TextStyle(color = LightTextColor, fontSize = 44.sp)
                )
                TimeTextField(
                    isBorderVisible = isMinuteFocused,
                    textFieldValue = minuteValue,
                    onTextFieldValueChange = { newValue ->
                        onMinuteChange.invoke(newValue)
                    },
                    onFocusChanged = { isFocused ->
                        onMinuteFocusChange.invoke(isFocused)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 28.dp)
                        .weight(1f),
                    text = "Час",
                    style = TextStyle(color = MainTextColor, fontSize = 14.sp)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f),
                    text = "Минуты",
                    style = TextStyle(color = MainTextColor, fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 28.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .clickable { onCancelClick.invoke() },
                    text = "Отмена",
                    style = TextStyle(color = AccentColor, fontSize = 16.sp)
                )
                Text(
                    modifier = Modifier.clickable { onConfirmClick.invoke() },
                    text = "OK",
                    style = TextStyle(color = AccentColor, fontSize = 16.sp)
                )
            }
        }
    }
}

@Composable
fun TimeTextField(
    isBorderVisible: Boolean,
    textFieldValue: String,
    modifier: Modifier = Modifier,
    onTextFieldValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {

    Box(
        modifier = modifier
            .width(84.dp)
            .height(68.dp)
            .background(
                color = DialogBoxTextFieldColor,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = if (isBorderVisible) AccentColor else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .onFocusChanged { focusState ->
                    onFocusChanged.invoke(focusState.isFocused)
                },
            value = textFieldValue,
            onValueChange = { newValue ->
                if (newValue.length <= 2) {
                    onTextFieldValueChange.invoke(newValue)
                }
            },
            textStyle = TextStyle(
                color = LightTextColor,
                fontSize = 34.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AccentColor
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Composable
fun AlarmBlock(

) {
    var isExtended by remember {
        mutableStateOf(false)
    }

    var isClockDialActivated by remember {
        mutableStateOf(false)
    }

    var clockDialValue by remember {
        mutableStateOf("09:45")
    }

    var clockInformationValue by remember {
        mutableStateOf("Каждый день")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CardColor, shape = RoundedCornerShape(12.dp))
    ) {
        ArrowButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp, top = 8.dp),
            isClosed = isExtended,
            onClick = { isExtended = !isExtended }
        )

        ClockDial(
            modifier = Modifier.padding(start = 16.dp),
            isActivated = isClockDialActivated,
            timeValue = clockDialValue,
            onClockDialClick = {}
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = clockInformationValue,
                style = TextStyle(
                    color = if (isClockDialActivated) MainTextColor else SubTextColor
                )
            )

            Switch(
                modifier = Modifier.align(Alignment.TopEnd),
                checked = isClockDialActivated,
                onCheckedChange = { isClockDialActivated = it },
                colors = SwitchDefaults.colors(
//                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = SubTextColor,
                    checkedTrackColor = AccentColor,
                    uncheckedTrackColor = LightTextColor
                )
            )
        }

    }
}

@Composable
fun ClockDial(
    modifier: Modifier = Modifier,
    isActivated: Boolean,
    timeValue: String,
    onClockDialClick: () -> Unit
) {
    Text(
        modifier = modifier.clickable { onClockDialClick.invoke() },
        text = timeValue,
        style = TextStyle(
            color = if (isActivated) MainTextColor else SubTextColor,
            fontSize = 48.sp
        )
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF261E26)
fun ClockDialPreview() {

    val isActivated by remember {
        mutableStateOf(true)
    }

    val timeValue by remember {
        mutableStateOf("09:45")
    }

    ClockDial(
        isActivated = isActivated,
        timeValue = timeValue,
        onClockDialClick = {}
    )
}

@Composable
@Preview(showBackground = true)
fun AlarmBlockPreview() {
    AlarmBlock()
}

@Composable
fun ArrowButton(
    modifier: Modifier = Modifier,
    isClosed: Boolean,
    onClick: () -> Unit
) {

    Box(modifier = modifier
        .clickable { onClick.invoke() }
        .background(color = DialogBoxTextFieldColor, shape = CircleShape)
    ) {
        if (isClosed) {
            Image(
                painter = painterResource(id = R.drawable.chevron_down),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LightTextColor)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.chevron_up),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LightTextColor)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ArrowButtonPreview() {
    var isClosedState by remember {
        mutableStateOf(false)
    }

    ArrowButton(
        isClosed = isClosedState,
        onClick = { isClosedState = !isClosedState }
    )
}

@Composable
@Preview(showBackground = true)
fun TimeTextFieldPreview() {
    var textFieldValue by remember {
        mutableStateOf("")
    }

    TimeTextField(
        isBorderVisible = false,
        textFieldValue = textFieldValue,
        onTextFieldValueChange = { newValue ->
            textFieldValue = newValue
        },
        onFocusChanged = {}
    )
}

@Composable
@Preview(showBackground = true)
fun DialogBoxPreview() {

    var hourValue by remember { mutableStateOf("") }
    var minuteValue by remember { mutableStateOf("") }

    var isMinuteFocused by remember { mutableStateOf(false) }
    var isHourFocused by remember { mutableStateOf(false) }

    DialogBox(
        width = 240.dp,
        height = 180.dp,
        roundCorner = 12.dp,
        backgroundColor = DialogBoxColor,
        hourValue = hourValue,
        minuteValue = minuteValue,
        onHourChange = { newHourValue ->
            hourValue = newHourValue
        },
        onMinuteChange = { newMinuteValue ->
            minuteValue = newMinuteValue
        },
        isHourFocused = isHourFocused,
        isMinuteFocused = isMinuteFocused,
        onHourFocusChange = { isFocused ->
            isHourFocused = isFocused
        },
        onMinuteFocusChange = { isFocused ->
            isMinuteFocused = isFocused
        },
        onConfirmClick = {},
        onCancelClick = {}
    )
}

@Composable
@Preview(showBackground = true)
fun RoundButtonPreview() {
    RoundButton(
        onClick = {}
    )
}
