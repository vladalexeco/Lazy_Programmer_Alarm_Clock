package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

class CreateWeeklyAlarmUseCase(
    private val alarmClockMaker: AlarmClockMaker
) {
    operator fun invoke(alarm: Alarm) {
        alarmClockMaker.createWeeklyAlarm(alarm = alarm)
    }
}