package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMakerImpl
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AlarmListScreen(
    state: AlarmListScreenState,
    onEvent: (AlarmListScreenEvent) -> Unit
) {
    val context = LocalContext.current
    val permissionGranted = remember { mutableStateOf(isPermissionGranted(context)) }

    var isVisibleSetTimeDialogBox by remember { mutableStateOf(false) }
    var defaultHourValue by remember { mutableStateOf("") }
    var defaultMinuteValue by remember { mutableStateOf("") }
    var isHourCellFocused by remember { mutableStateOf(false) }
    var isMinuteCellFocused by remember { mutableStateOf(false) }

    var currentAlarmIndex: Int? by remember { mutableStateOf(null) }
    var indexOfCurrentBlock: Int? by remember { mutableStateOf(null) }

    val alarmClockMaker = AlarmClockMakerImpl(context = LocalContext.current)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permissionGranted.value = true

            currentAlarmIndex = null
            isVisibleSetTimeDialogBox = !isVisibleSetTimeDialogBox
            defaultHourValue = ""
            defaultMinuteValue = ""

        } else {
            showEducationalDialog(context)
        }
    }

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
                            val currentAlarm = state.alarms[index]

                            alarmClockMaker.cancelAlarm(currentAlarm)

                            onEvent.invoke(AlarmListScreenEvent.DeleteAlarmEvent(currentAlarm))
                        },
                        onSwitchClick = { isChecked ->
                            val newAlarm = state.alarms[index].copy(isActivated = isChecked)

                            onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(newAlarm))

                            if (isChecked) {
                                alarmClockMaker.createWeeklyAlarm(newAlarm)
                            } else {
                                alarmClockMaker.cancelAlarm(newAlarm)
                            }
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

                            alarmClockMaker.createWeeklyAlarm(newAlarm)
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
                if (!permissionGranted.value) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    currentAlarmIndex = null
                    isVisibleSetTimeDialogBox = !isVisibleSetTimeDialogBox
                    defaultHourValue = ""
                    defaultMinuteValue = ""
                }
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

                        alarmClockMaker.createWeeklyAlarm(alarm = newAlarm)
                    } else {
                        val currentAlarm = state.alarms[currentAlarmIndex!!]

                        val modifiedAlarm = currentAlarm.copy(hour = defaultHourValue, minute = defaultMinuteValue)

                        onEvent.invoke(AlarmListScreenEvent.SaveAlarmEvent(modifiedAlarm))

                        alarmClockMaker.createWeeklyAlarm(alarm = modifiedAlarm)
                    }

                    isVisibleSetTimeDialogBox = !isVisibleSetTimeDialogBox
                }
            )
        }
    }
}

fun isPermissionGranted(context: Context) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview(showBackground = true)
fun AlarmListScreenPreview() {
    AlarmListScreen(
        state = AlarmListScreenState(),
        onEvent = {}
    )
}

fun showEducationalDialog(context: Context) {
    Toast.makeText(context, "No permissions", Toast.LENGTH_SHORT).show()
}



