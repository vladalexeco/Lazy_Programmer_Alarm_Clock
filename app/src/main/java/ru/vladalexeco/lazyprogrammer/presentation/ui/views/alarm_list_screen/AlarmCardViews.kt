package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxTextFieldColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.SubTextColor

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
fun DaysAlarmInfoView(
    modifier: Modifier = Modifier,
    textMessage: String,
    isClockDialActivated: Boolean,
) {
    Text(
        modifier = modifier,
        text = textMessage,
        style = TextStyle(
            color = if (isClockDialActivated) MainTextColor else SubTextColor
        )
    )
}

@Composable
@Preview(showBackground = true)
fun DaysAlarmInfoViewPreview() {
    DaysAlarmInfoView(
        textMessage = "Каждый день",
        isClockDialActivated = false
    )
}

@Composable
fun ClockDial(
    modifier: Modifier = Modifier,
    isActivated: Boolean,
    hourValue: String,
    minuteValue: String,
    onClockDialClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClockDialClick.invoke() }
    ) {
        Text(
            text = hourValue,
            style = TextStyle(
                color = if (isActivated) MainTextColor else SubTextColor,
                fontSize = 48.sp
            )
        )

        Text(
            text = ":",
            style = TextStyle(
                color = if (isActivated) MainTextColor else SubTextColor,
                fontSize = 48.sp
            )
        )

        Text(
            text = minuteValue,
            style = TextStyle(
                color = if (isActivated) MainTextColor else SubTextColor,
                fontSize = 48.sp
            )
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF261E26)
fun ClockDialPreview() {

    val isActivated by remember {
        mutableStateOf(true)
    }

    val hourValue by remember { mutableStateOf("09") }
    val minuteValue by remember { mutableStateOf("45") }

    ClockDial(
        isActivated = isActivated,
        hourValue = hourValue,
        minuteValue = minuteValue,
        onClockDialClick = {}
    )
}