package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor

@Composable
fun SimpleButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LightTextColor,
    textColor: Color = DialogBoxColor,
    text: String,
    width: Dp = 72.dp,
    height: Dp = 40.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(color = textColor)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SimpleButtonPreview() {
    SimpleButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Press me",
        onClick = {}
    )
}