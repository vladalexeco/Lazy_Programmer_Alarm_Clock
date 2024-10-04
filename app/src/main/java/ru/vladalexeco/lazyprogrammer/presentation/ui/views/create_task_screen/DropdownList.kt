package ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor

@Composable
fun DropdownList(
    modifier: Modifier = Modifier,
    items: List<String>,
    value: String? = null,
    hint: String,
    onItemSelect: (String) -> Unit
) {

    val currentValue = value ?: ""

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(currentValue) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = modifier) {

        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            readOnly = true,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center),
                    text = hint,
                    style = TextStyle(fontSize = 20.sp)
                )
            },
            modifier = Modifier
                .width(144.dp)
                .border(width = 1.dp, color = AccentColor, shape = RoundedCornerShape(4.dp))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { expanded = !expanded },
                    imageVector = icon,
                    contentDescription = "contentDescription",
                    tint = AccentColor
                )
            },
            textStyle = TextStyle(
                color = AccentColor,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                        onItemSelect.invoke(label)
                    },
                    text = {
                        Text(text = label)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MyContentPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "Выпадающее меню",
                style = TextStyle(color = AccentColor, fontSize = 20.sp)
            )

            DropdownList(
                modifier = Modifier.align(Alignment.CenterEnd),
                items = listOf("Delhi", "Mumbai", "Chennai"),
                hint = "kotlin",
                onItemSelect = {}
            )
        }
    }
}

