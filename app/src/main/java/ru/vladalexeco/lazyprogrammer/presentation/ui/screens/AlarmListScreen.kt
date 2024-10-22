package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.generateUniqueId
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenState
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
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

    var currentAlarmIndex: Int? by remember { mutableStateOf(null) }
    var indexOfCurrentBlock: Int? by remember { mutableStateOf(null) }

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
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(state.alarms) { index, alarm ->
                    AlarmBlock(
                        hourValue = alarm.hour,
                        minuteValue = alarm.minute,
                        isExtended = state.alarmExtendedValueList[index],
                        isActivated = alarm.isActivated,
                        weekdays = alarm.weekdays,
                        onClockClick = {
                            currentAlarmIndex = index

                            val currentAlarm = state.alarms[index]

                            defaultHourValue = currentAlarm.hour
                            defaultMinuteValue = currentAlarm.minute

                            isVisibleSetTimeDialogBox = !isVisibleSetTimeDialogBox
                        },
                        onDeleteClick = {
                            onEvent.invoke(AlarmListScreenEvent.DeleteAlarmEvent(state.alarms[currentAlarmIndex!!]))
                        },
                        onSwitchClick = { isChecked ->
                            val newAlarm = state.alarms[index].copy(isActivated = isChecked)

                            onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(newAlarm))
                        },
                        onExtendChange = {
                            currentAlarmIndex = index

                            val currentAlarm = state.alarms[index]
                            val currentExtendValue = currentAlarm.isExtended
                            val newAlarm = currentAlarm.copy(isExtended = !currentExtendValue)

                            onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(alarm = newAlarm))

                            if (indexOfCurrentBlock != index && indexOfCurrentBlock != null) {
                                val currentAlarmForClose = state.alarms[indexOfCurrentBlock!!]
                                val newCurrentAlarmForClose = currentAlarmForClose.copy(isExtended = false)

                                onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(alarm = newCurrentAlarmForClose))
                            }

                            indexOfCurrentBlock = index
                        },
                        onWeekdaysChange = { letterMap ->
                            val currentAlarm = state.alarms[currentAlarmIndex!!]
                            val newWeekdays = letterMap.values.toList()
                            val newAlarm = currentAlarm.copy(weekdays = newWeekdays, isExtended = true)

                            onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(alarm = newAlarm))
                        }
                    )
                }
            }
        }

        AddAlarmButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 84.dp),
            onClick = {
                currentAlarmIndex = null
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

                    if (currentAlarmIndex == null) {

                        val newAlarm = Alarm(
                            id = generateUniqueId(),
                            hour = hourValue,
                            minute = minuteValue,
                            weekdays = listOf(true, true, true, true, true, true, true),
                            isExtended = false,
                            isActivated = true,
                        )

                        onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(newAlarm))
                    } else {
                        val currentAlarm = state.alarms[currentAlarmIndex!!]

                        val modifiedAlarm = currentAlarm.copy(hour = defaultHourValue, minute = defaultMinuteValue)

                        onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(modifiedAlarm))
                    }

                    isVisibleSetTimeDialogBox = !isVisibleSetTimeDialogBox
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



