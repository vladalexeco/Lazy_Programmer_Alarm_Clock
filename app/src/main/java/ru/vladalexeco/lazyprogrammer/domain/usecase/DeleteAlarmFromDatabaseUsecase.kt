package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import javax.inject.Inject

class DeleteAlarmFromDatabaseUseSase @Inject constructor(
    private val alarmStorageRepository: AlarmStorageRepository
) {
    suspend operator fun invoke(alarm: Alarm) {
        alarmStorageRepository.deleteAlarmFromDatabase(alarm)
    }
}