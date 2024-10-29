package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

class DeleteAlarmFromDatabaseUseSase(
    private val alarmStorageRepository: AlarmStorageRepository
) {
    suspend operator fun invoke(alarm: Alarm) {
        alarmStorageRepository.deleteAlarmFromDatabase(alarm)
    }
}