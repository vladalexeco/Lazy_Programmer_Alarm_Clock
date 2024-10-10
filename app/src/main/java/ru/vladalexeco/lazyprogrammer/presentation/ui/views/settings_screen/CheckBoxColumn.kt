package ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckBoxColumn(
    modifier: Modifier = Modifier,
    listOfLabels: List<String>,
    initialValues: Map<String,Boolean>,
    onCheckBoxClick: (String, Boolean) -> Unit
) {


    Column(
        modifier = modifier
    ) {
        listOfLabels.indices.forEach { index ->
            initialValues[listOfLabels[index]]?.let {
                CheckBoxWithLabel(
                    selected = it,
                    label = listOfLabels[index],
                    onCheckBoxClick = { key, isChecked ->
                        onCheckBoxClick.invoke(key, isChecked)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun CheckBoxColumnPreview() {
    val listOfLabels = listOf("kotlin", "java", "python")
    val initialValues: MutableMap<String, Boolean> = remember { mutableStateMapOf() }

    listOfLabels.forEach { item -> initialValues[item] = true }

    CheckBoxColumn(
        modifier = Modifier.fillMaxWidth(),
        listOfLabels = listOfLabels,
        initialValues = initialValues,
        onCheckBoxClick = { key, isChecked ->
            initialValues[key] = isChecked
        }
    )
}