package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxTextFieldColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.SubTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.RoundButton

@Composable
fun AlarmListScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 12.dp, end = 12.dp)
            ) {
                Text(
                    text = "Будильник",
                    style = TextStyle(color = MainTextColor, fontSize = 24.sp)
                )
                Image(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.menu_dots_vertical),
                    colorFilter = ColorFilter.tint(MainTextColor),
                    contentDescription = null
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 12.dp)
                    .background(SubTextColor)
            ) {

            }
        }

        RoundButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            onClick = {}
        )
    }
}


@Composable
@Preview(showBackground = true)
fun AlarmListScreenPreview() {
    AlarmListScreen()
}



