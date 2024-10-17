package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenState
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.SubTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen.AddAlarmButton
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen.AlarmBlock
import ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen.SetTimeDialogBox
import ru.vladalexeco.lazyprogrammer.presentation.viewmodel.AlarmListScreenViewModel

@Composable
fun AlarmListScreen() {

    val viewModel: AlarmListScreenViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    AlarmListScreen(
        state = state,
        onEvent = { alarmListScreenEvent ->
            viewModel.onEvent(alarmListScreenEvent)
        }
    )
}

@Composable
fun AlarmListScreen(
    state: AlarmListScreenState,
    onEvent: (AlarmListScreenEvent) -> Unit
) {
    var isVisibleSetTimeDialogBox by remember { mutableStateOf(false) }
    var defaultHourValue by remember { mutableStateOf("") }
    var defaultMinuteValue by remember { mutableStateOf("") }
    var isHourCellFocused by remember { mutableStateOf(false) }
    var isMinuteCellFocused by remember { mutableStateOf(false) }

    var currentAlarmId: String? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 12.dp, end = 12.dp)
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
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 84.dp
                    )
            ) {
                itemsIndexed(state.alarms) { index, alarm ->
                    AlarmBlock(
                        index = index,
                        hourValue = alarm.hour,
                        minuteValue = alarm.minute,
                        isActivated = alarm.isActivated,
                        weekdays = alarm.weekdays,
                        onClockClick = {},
                        onDeleteClick = {},
                        onSwitchClick = {}
                    )
                }
            }
        }

        AddAlarmButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 84.dp),
            onClick = {
                currentAlarmId = null
                // TODO каждому AlarmCard мы присваиваем id Alarm.
                // TODO при нажатии на часы AlarmCard мы меняем currentAlarmId
                // TODO при нажатии на плюсик или на часы AlarmCard мы проверяем currentAlarmId
                isVisibleSetTimeDialogBox = !isVisibleSetTimeDialogBox
                defaultHourValue = ""
                defaultMinuteValue = ""
            }
        )

        if (isVisibleSetTimeDialogBox) {
            SetTimeDialogBox(
                modifier = Modifier.align(Alignment.Center),
                hourValue = defaultHourValue,
                minuteValue = defaultMinuteValue,
                isHourFocused = isHourCellFocused,
                isMinuteFocused = isMinuteCellFocused,
                onHourFocusChange = { isFocused ->
                    isHourCellFocused = isFocused
                },
                onMinuteFocusChange = { isFocused ->
                    isMinuteCellFocused = isFocused
                },
                onHourChange = { newValue ->
                    defaultHourValue = newValue
                },
                onMinuteChange = { newValue ->
                    defaultMinuteValue = newValue
                },
                onCancelClick = {
                    isVisibleSetTimeDialogBox = false
                },
                onConfirmClick = { hourValue, minuteValue ->
                    if (currentAlarmId == null) {

                        val newAlarm = Alarm(
                            hour = hourValue,
                            minute = minuteValue,
                            weekdays = listOf(true, true, true, true, true, true, true),
                            isActivated = true,
                        )

                        onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(alarm = newAlarm))
                    } else {
                        // TODO меняем уже существующий
                    }
                }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AlarmListScreenPreview() {
    AlarmListScreen(
        state = AlarmListScreenState(),
        onEvent = {}
    )
}



