package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

class CancelAlarmUseCase(
    private val alarmClockMaker: AlarmClockMaker
) {
    operator fun invoke(alarm: Alarm) {
        alarmClockMaker.cancelAlarm(alarm = alarm)
    }
}