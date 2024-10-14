package ru.vladalexeco.lazyprogrammer.presentation.ui.views.bottom_navigation_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor

@Composable
fun NavBottomElement(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onFocusTint: Color,
    outFocusTint: Color,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Column(
        modifier = modifier
            .width(75.dp)
            .height(72.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    if (!isSelected) {
                        onClick.invoke()
                    }
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painter,
            contentDescription = null,
            tint = if (isSelected) onFocusTint else outFocusTint,
        )
        Text(
            text = text,
            color = if (isSelected) onFocusTint else outFocusTint,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.padding(bottom = 4.dp),
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun NavBottomElementPreview() {
    var isSelected by remember { mutableStateOf(false) }

    NavBottomElement(
        text = "Настройки",
        painter = painterResource(R.drawable.settings_icon),
        onFocusTint = AccentColor,
        outFocusTint = MainTextColor,
        isSelected = isSelected,
        onClick = { isSelected = !isSelected }
    )
}