package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.AlarmTaskStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask

class SaveAlarmTaskToDatabaseUseCase(
    private val alarmTaskStorageRepository: AlarmTaskStorageRepository
) {
    suspend operator fun invoke(alarmTask: AlarmTask) {
        alarmTaskStorageRepository.saveAlarmTaskToDatabase(alarmTask = alarmTask)
    }
}