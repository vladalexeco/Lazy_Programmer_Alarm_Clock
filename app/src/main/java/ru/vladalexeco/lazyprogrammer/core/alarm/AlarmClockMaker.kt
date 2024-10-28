package ru.vladalexeco.lazyprogrammer.core.alarm

import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

interface AlarmClockMaker {
    fun createWeeklyAlarm(alarm: Alarm)
    fun editAlarm(alarm: Alarm)
    fun cancelAlarm(alarm: Alarm)
}