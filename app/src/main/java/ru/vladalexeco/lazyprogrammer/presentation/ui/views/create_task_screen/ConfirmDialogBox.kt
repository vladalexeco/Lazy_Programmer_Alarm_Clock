package ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.WrongAnswerColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_task_screen.SimpleButton

@Composable
fun ConfirmDialogBox(
    modifier: Modifier = Modifier,
    width: Dp = 240.dp,
    height: Dp = 180.dp,
    backgroundColor: Color = DialogBoxColor,
    text: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 32.dp),
            text = text,
            style = TextStyle(color = MainTextColor, fontSize = 16.sp)
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 6.dp, end = 6.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SimpleButton(
                modifier = Modifier.weight(1f),
                backgroundColor = RightAnswerColor,
                textColor = MainTextColor,
                text = "Ok",
                onClick = { onConfirm.invoke() }
            )

            SimpleButton(
                modifier = Modifier.weight(1f),
                backgroundColor = WrongAnswerColor,
                textColor = MainTextColor,
                text = "Cancel",
                onClick = { onCancel.invoke() }
            )
        }
    }
}

@Composable
@Preview
fun ConfirmDialogBoxPreview() {
    ConfirmDialogBox(
        text = "Очистить поля?",
        onConfirm = {},
        onCancel = {}
    )
}