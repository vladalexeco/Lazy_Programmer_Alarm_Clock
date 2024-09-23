package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxTextFieldColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor

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
fun SetTimeDialogBox(
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
@Preview(showBackground = true)
fun SetTimeDialogBoxPreview() {

    var hourValue by remember { mutableStateOf("") }
    var minuteValue by remember { mutableStateOf("") }

    var isMinuteFocused by remember { mutableStateOf(false) }
    var isHourFocused by remember { mutableStateOf(false) }

    SetTimeDialogBox(
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