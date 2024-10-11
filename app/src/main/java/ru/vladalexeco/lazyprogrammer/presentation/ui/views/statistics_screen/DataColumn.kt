package ru.vladalexeco.lazyprogrammer.presentation.ui.views.statistics_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.RightAnswerColor

@Composable
fun DataColumn(
    modifier: Modifier = Modifier,
    dataMap: Map<String, Int>
) {

    LazyColumn(
        modifier = modifier
    ) {
        items(dataMap.keys.toList()) { item ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    horizontal = 16.dp, vertical = 8.dp
                )
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item,
                    style = TextStyle(color = AccentColor, fontSize = 20.sp)
                )

                Text(
                    text = dataMap[item].toString(),
                    style = TextStyle(color = RightAnswerColor, fontSize = 20.sp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun DataColumnPreview() {

    val dataMap: Map<String, Int> = linkedMapOf(
        "kotlin" to 7,
        "java" to 11,
        "python" to 9
    )

    DataColumn(
        modifier = Modifier.fillMaxWidth(),
        dataMap = dataMap
    )
}