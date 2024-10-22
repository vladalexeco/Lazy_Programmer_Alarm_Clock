package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

@Composable
@Preview
fun AlarmBlockColumnPreview() {

    var indexOfCurrentBlock: Int? by remember { mutableStateOf(null) }

    val listOfAlarms = remember {
        mutableStateListOf(
            Alarm(
                id = "a",
                hour = "23",
                minute = "02",
                weekdays = listOf(true, true, true, true, true, true, true),
                isExtended = false,
                isActivated = true,
                melody = null
            ),
            Alarm(
                id = "b",
                hour = "14",
                minute = "17",
                weekdays = listOf(true, true, true, true, true, true, true),
                isExtended = false,
                isActivated = true,
                melody = null
            ),
            Alarm(
                id = "c",
                hour = "01",
                minute = "15",
                weekdays = listOf(true, true, true, true, true, true, true),
                isExtended = false,
                isActivated = true,
                melody = null
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(listOfAlarms) { alarmIndex, alarm ->
            AlarmBlock(
                isExtended = alarm.isExtended,
                hourValue = alarm.hour,
                minuteValue = alarm.minute,
                isActivated = alarm.isActivated,
                weekdays = alarm.weekdays,
                onExtendChange = {
                    val currentAlarm = listOfAlarms[alarmIndex]
                    val isExtendedInCurrentPosition = currentAlarm.isExtended

                    listOfAlarms[alarmIndex] = currentAlarm.copy(isExtended = !isExtendedInCurrentPosition)

                    if (indexOfCurrentBlock != alarmIndex && indexOfCurrentBlock != null) {
                        val currentAlarmForClose = listOfAlarms[indexOfCurrentBlock!!]
                        listOfAlarms[indexOfCurrentBlock!!] = currentAlarmForClose.copy(isExtended = false)
                    }

                    indexOfCurrentBlock = alarmIndex
                },
                onSwitchClick = {},
                onClockClick = {},
                onDeleteClick = {},
                onWeekdaysChange = { letterMap ->

                }
            )
        }
    }
}