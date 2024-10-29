package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import javax.inject.Inject

class ScheduleAlarmForDayUseCase @Inject constructor(
    private val alarmClockMaker: AlarmClockMaker
) {
    operator fun invoke(alarm: Alarm, dayOfWeek: Int) {
        alarmClockMaker.scheduleAlarmForDay(alarm = alarm, dayOfWeek = dayOfWeek)
    }
}