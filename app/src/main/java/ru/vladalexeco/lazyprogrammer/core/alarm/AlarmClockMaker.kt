package ru.vladalexeco.lazyprogrammer.core.alarm

import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

interface AlarmClockMaker {
    fun createAlarm(alarm: Alarm, triggerTime: Long)
    fun editAlarm(alarm: Alarm)
    fun cancelAlarm(alarm: Alarm)
}