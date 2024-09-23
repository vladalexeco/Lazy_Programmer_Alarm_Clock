package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor

@Composable
fun AddAlarmButton(
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
@Preview(showBackground = true)
fun AddAlarmButtonPreview() {
    AddAlarmButton(
        onClick = {}
    )
}