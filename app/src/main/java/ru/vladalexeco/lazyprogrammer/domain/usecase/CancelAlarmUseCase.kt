package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import javax.inject.Inject

class CancelAlarmUseCase @Inject constructor(
    private val alarmClockMaker: AlarmClockMaker
) {
    operator fun invoke(alarm: Alarm) {
        alarmClockMaker.cancelAlarm(alarm = alarm)
    }
}