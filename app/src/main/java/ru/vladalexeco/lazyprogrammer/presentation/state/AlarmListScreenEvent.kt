package ru.vladalexeco.lazyprogrammer.presentation.state

import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

sealed interface AlarmListScreenEvent {
    data class SaveAlarmEvent(val alarm: Alarm) : AlarmListScreenEvent
    data class DeleteAlarmEvent(val alarm: Alarm) : AlarmListScreenEvent
    data class PauseAlarmEvent(val alarm: Alarm) : AlarmListScreenEvent
    data class RestoreAlarmEvent(val alarm: Alarm) : AlarmListScreenEvent
    data class SaveAlarmToDatabaseWithoutCreatingAlarmActionEvent(val alarm: Alarm) : AlarmListScreenEvent
}