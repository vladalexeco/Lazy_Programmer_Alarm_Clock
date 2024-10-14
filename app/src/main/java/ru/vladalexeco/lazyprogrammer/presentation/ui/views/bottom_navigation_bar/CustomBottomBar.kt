package ru.vladalexeco.lazyprogrammer.presentation.ui.views.bottom_navigation_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BottomBarColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor

@Composable
fun CustomBottomBar(
    backgroundColor: Color = BottomBarColor,
    topEdgeColor: Color = DialogBoxColor,
    screenRoute1: String = "",
    screenRoute2: String = "",
    screenRoute3: String = "",
    screenRoute4: String = "",
    currentRoute: String? = "",
    onFocusTint: Color = AccentColor,
    outFocusTint: Color = MainTextColor,
    firstItemText: String = "Будильник",
    secondItemText: String = "Задание",
    thirdItemText: String = "Результаты",
    fourthItemText: String = "Настройки",
    firstItemImage: Painter = painterResource(R.drawable.alarm_list_icon),
    secondItemImage: Painter = painterResource(R.drawable.create_task_icon),
    thirdItemImage: Painter = painterResource(R.drawable.statistic_icon),
    fourthItemImage: Painter = painterResource(R.drawable.settings_icon),
    firstItemClick: () -> Unit,
    secondItemClick: () -> Unit,
    thirdItemClick: () -> Unit,
    fourthItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = topEdgeColor)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NavBottomElement(
                text = firstItemText,
                painter = firstItemImage,
                onFocusTint = onFocusTint,
                outFocusTint = outFocusTint,
                isSelected = currentRoute == screenRoute1,
                onClick = { firstItemClick.invoke() }
            )

            NavBottomElement(
                text = secondItemText,
                painter = secondItemImage,
                onFocusTint = onFocusTint,
                outFocusTint = outFocusTint,
                isSelected = currentRoute == screenRoute2,
                onClick = { secondItemClick.invoke() }
            )

            NavBottomElement(
                text = thirdItemText,
                painter = thirdItemImage,
                onFocusTint = onFocusTint,
                outFocusTint = outFocusTint,
                isSelected = currentRoute == screenRoute3,
                onClick = { thirdItemClick.invoke() }
            )

            NavBottomElement(
                text = fourthItemText,
                painter = fourthItemImage,
                onFocusTint = onFocusTint,
                outFocusTint = outFocusTint,
                isSelected = currentRoute == screenRoute4,
                onClick = { fourthItemClick.invoke() }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FullScreenCustomBottomBar() {

    var currentRoute by remember { mutableStateOf("one") }

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                screenRoute1 = "one",
                screenRoute2 = "two",
                screenRoute3 = "three",
                screenRoute4 = "four",
                currentRoute = currentRoute,
                firstItemClick = { currentRoute = "one" },
                secondItemClick = { currentRoute = "two" },
                thirdItemClick = { currentRoute = "three" },
                fourthItemClick = { currentRoute = "four" }
            )

        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = BackgroundColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Content",
                    style = TextStyle(color = MainTextColor, fontSize = 20.sp)
                )
            }
        }
    )
}