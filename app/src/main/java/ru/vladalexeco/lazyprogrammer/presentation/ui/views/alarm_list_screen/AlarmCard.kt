package ru.vladalexeco.lazyprogrammer.presentation.ui.views.alarm_list_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.modifyMapToString
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.CardColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.DialogBoxTextFieldColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.LightTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.SubTextColor

@Composable
fun AlarmBlock(
    isExtended: Boolean,
    hourValue: String,
    minuteValue: String,
    isActivated: Boolean,
    weekdays: List<Boolean>,
    onExtendChange: () -> Unit,
    onClockClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSwitchClick: (Boolean) -> Unit,
    onWeekdaysChange: (Map<String, Boolean>) -> Unit
) {

    var isClockDialActivated by remember {
        mutableStateOf(isActivated)
    }

    var clockInformationValue by remember {
        mutableStateOf("Каждый день")
    }

    var alarmMelodyName by remember {
        mutableStateOf("Выбрать мелодию")
    }

    var isCheckedOne by remember { mutableStateOf(weekdays[0]) }
    var isCheckedTwo by remember { mutableStateOf(weekdays[1]) }
    var isCheckedThree by remember { mutableStateOf(weekdays[2]) }
    var isCheckedFour by remember { mutableStateOf(weekdays[3]) }
    var isCheckedFive by remember { mutableStateOf(weekdays[4]) }
    var isCheckedSix by remember { mutableStateOf(weekdays[5]) }
    var isCheckedSeven by remember { mutableStateOf(weekdays[6]) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CardColor, shape = RoundedCornerShape(12.dp))
            .animateContentSize()
    ) {
        ArrowButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp, top = 8.dp),
            isClosed = isExtended,
            onClick = {
                onExtendChange.invoke()
            }
        )

        ClockDial(
            modifier = Modifier.padding(start = 16.dp),
            isActivated = isClockDialActivated,
            hourValue = hourValue,
            minuteValue = minuteValue,
            onClockDialClick = { onClockClick.invoke() }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        ) {

            DaysAlarmInfoView(
                modifier = Modifier.align(Alignment.CenterStart),
                textMessage = clockInformationValue,
                isClockDialActivated = isClockDialActivated
            )

            Switch(
                modifier = Modifier.align(Alignment.TopEnd),
                checked = isClockDialActivated,
                onCheckedChange = { isActivated ->
                    isClockDialActivated = isActivated
                    onSwitchClick.invoke(isActivated)
                },
                colors = SwitchDefaults.colors(
//                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = SubTextColor,
                    checkedTrackColor = AccentColor,
                    uncheckedTrackColor = LightTextColor
                )
            )
        }
        if (isExtended) {
            WeekDaysRow(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                isCheckedOne = isCheckedOne,
                isCheckedTwo = isCheckedTwo,
                isCheckedThree = isCheckedThree,
                isCheckedFour = isCheckedFour,
                isCheckedFive = isCheckedFive,
                isCheckedSix = isCheckedSix,
                isCheckedSeven = isCheckedSeven,
                onClickOne = { isCheckedOne = !isCheckedOne },
                onClickTwo = { isCheckedTwo = !isCheckedTwo },
                onClickThree = { isCheckedThree = !isCheckedThree },
                onClickFour = { isCheckedFour = !isCheckedFour },
                onClickFive = { isCheckedFive = !isCheckedFive },
                onClickSix = { isCheckedSix = !isCheckedSix },
                onClickSeven = { isCheckedSeven = !isCheckedSeven },
                onDaysArrayChange = { letterMap ->
                    clockInformationValue = modifyMapToString(letterMap)
                    onWeekdaysChange.invoke(letterMap)
                }
            )

            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 24.dp)
                    .clickable { },
                text = alarmMelodyName,
                style = TextStyle(color = MainTextColor)
            )

            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
                    .clickable { onDeleteClick.invoke() },
                text = "Удалить",
                style = TextStyle(color = MainTextColor)
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun AlarmBlockPreview() {

    var alarm by remember {
        mutableStateOf(
            Alarm(
                id = "c",
                hour = "23",
                minute = "02",
                weekdays = listOf(true, true, true, true, true, true, true),
                isExtended = true,
                isActivated = true,
                melody = null
            )
        )
    }

    AlarmBlock(
        isExtended = alarm.isExtended,
        hourValue = alarm.hour,
        minuteValue = alarm.minute,
        isActivated = alarm.isActivated,
        weekdays = alarm.weekdays,
        onExtendChange = {
            val currentExtended = alarm.isExtended

            alarm = alarm.copy(isExtended = !currentExtended)
        },
        onSwitchClick = {},
        onClockClick = {},
        onDeleteClick = {},
        onWeekdaysChange = { letterMap ->

        }
    )
}












