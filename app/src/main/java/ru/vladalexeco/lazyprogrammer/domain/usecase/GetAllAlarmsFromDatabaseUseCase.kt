package ru.vladalexeco.lazyprogrammer.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

class GetAllAlarmsFromDatabaseUseCase(
    private val alarmStorageRepository: AlarmStorageRepository
) {
    suspend operator fun invoke(): Flow<List<Alarm>> {
        return alarmStorageRepository.getAllAlarmFromDatabase()
    }
}