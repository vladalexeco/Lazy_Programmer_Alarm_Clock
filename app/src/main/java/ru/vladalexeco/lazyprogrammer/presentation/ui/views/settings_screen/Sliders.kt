package ru.vladalexeco.lazyprogrammer.presentation.ui.views.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import kotlin.math.roundToInt

@Composable
fun EstimateRangeSlider(
    modifier: Modifier = Modifier,
    initialStart: Int,
    initialEnd: Int,
    onRangeChanged: (Int, Int) -> Unit,
    onRangeChangeFinish: (Int, Int) -> Unit
) {
    val start = initialStart.toFloat()
    val end = initialEnd.toFloat()

    var sliderPosition by remember { mutableStateOf(start..end) }

    RangeSlider(
        modifier = modifier,
        value = sliderPosition,
        colors = SliderDefaults.colors(
            thumbColor = AccentColor,
            activeTrackColor = AccentColor,
            inactiveTickColor = MainTextColor
        ),
        steps = 8,
        onValueChange = { range ->
            sliderPosition = range
            onRangeChanged.invoke(
                sliderPosition.start.toInt(),
                sliderPosition.endInclusive.toInt()
            )
        },
        valueRange = 1f..10f,
        onValueChangeFinished = {
            onRangeChangeFinish.invoke(
                sliderPosition.start.toInt(),
                sliderPosition.endInclusive.toInt()
            )
        },
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun EstimateRangeSliderPreview() {

    var start by remember { mutableIntStateOf(1) }
    var end by remember { mutableIntStateOf(5) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EstimateRangeSlider(
            modifier = Modifier.fillMaxWidth(),
            initialStart = start,
            initialEnd = end,
            onRangeChanged = { rangeStart, rangeEnd ->
                start = rangeStart
                end = rangeEnd
            },
            onRangeChangeFinish = { rangeStart, rangeEnd ->

            }
        )

        Text(
            text = "Value range is $start - $end",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )
    }
}

@Composable
fun EstimateSlider(
    modifier: Modifier = Modifier,
    start: Int,
    onValueChange: (Int) -> Unit,
    onValueChangeFinish: (Int) -> Unit
) {
    val startValue = start.toFloat()

    var sliderPosition by remember { mutableFloatStateOf(startValue) }

    Slider(
        modifier = modifier,
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            onValueChange.invoke(sliderPosition.toInt())
        },
        onValueChangeFinished = {
            onValueChangeFinish.invoke(sliderPosition.toInt())
        },
        colors = SliderDefaults.colors(
            thumbColor = AccentColor,
            activeTrackColor = AccentColor,
            inactiveTrackColor = MainTextColor,
        ),
        steps = 8,
        valueRange = 1f..10f
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1D181C)
fun EstimateSliderPreview() {

    var startValue by remember { mutableIntStateOf(3) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EstimateSlider(
            modifier = Modifier.fillMaxWidth(),
            start = startValue,
            onValueChange = { newValue ->
                startValue = newValue
            },
            onValueChangeFinish = { newValue ->

            }
        )

        Text(
            text = "Value is $startValue",
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )
    }
}
