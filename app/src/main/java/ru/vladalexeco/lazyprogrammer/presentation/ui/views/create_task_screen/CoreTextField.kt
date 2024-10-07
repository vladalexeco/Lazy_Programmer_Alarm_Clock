package ru.vladalexeco.lazyprogrammer.presentation.ui.views.create_task_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor

@Composable
fun CoreTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    hint: String,
    index: Int = 0,
    onValueChange: (Int, String) -> Unit,
) {

    OutlinedTextField(
        modifier = modifier
            .width(70.dp)
            .border(width = 1.dp, color = AccentColor, shape = RoundedCornerShape(4.dp)),
        value = textValue,
        placeholder = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                text = hint,
                style = TextStyle(fontSize = 20.sp)
            )
        },
        onValueChange = { newTextValue ->
            onValueChange.invoke(index, newTextValue)
        },
        textStyle = TextStyle(
            color = AccentColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun CoreTextFieldPreview() {

    var textValue by remember { mutableStateOf("") }

    CoreTextField(
        textValue = textValue,
        hint = "true",
        onValueChange = { _, newTextValue ->
            textValue = newTextValue
        }
    )
}