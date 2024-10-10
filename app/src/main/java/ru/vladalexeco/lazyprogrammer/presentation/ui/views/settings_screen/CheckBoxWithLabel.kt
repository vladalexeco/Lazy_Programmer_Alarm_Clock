package ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.SubTextColor

@Composable
fun DefaultCheckBox(
    selected: Boolean,
    onValueChange: (Boolean) -> Unit
) {

    Checkbox(
            checked = selected,
            colors = CheckboxDefaults.colors(
                checkedColor = AccentColor,
                uncheckedColor = SubTextColor,
                checkmarkColor = Color.White
            ),
            onCheckedChange = { isSelected ->
                onValueChange.invoke(isSelected)
            }
        )
}

@Composable
@Preview(showBackground = true)
fun DefaultCheckBoxPreview() {
    var selected by remember { mutableStateOf(true) }

    DefaultCheckBox(
        selected = selected,
        onValueChange = { isSelected ->
            selected = isSelected
        }
    )
}

@Composable
fun CheckBoxWithLabel(
    modifier: Modifier = Modifier,
    selected: Boolean,
    label: String,
    onCheckBoxClick: (String, Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f).padding(start = 16.dp),
            text = label,
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        DefaultCheckBox(
            selected = selected,
            onValueChange = { isChecked ->
                onCheckBoxClick(label, isChecked)
            }
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun CheckBoxWithLabelPreview() {
    var selected by remember { mutableStateOf(false) }

    CheckBoxWithLabel(
        modifier = Modifier.fillMaxWidth(),
        selected = selected,
        label = "kotlin",
        onCheckBoxClick = { label, isChecked ->
            selected = isChecked
        }
    )
}