package ru.vladalexeco.lazyprogrammer.presentation.state

import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

data class AlarmListScreenState(
    val alarms: List<Alarm> = emptyList()
)