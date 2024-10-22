package ru.vladalexeco.lazyprogrammer.domain.api

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

interface AlarmStorageRepository {
    suspend fun saveAlarmToDatabase(alarm: Alarm)
    suspend fun getAllAlarmFromDatabase(): Flow<List<Alarm>>
    suspend fun deleteAlarmFromDatabase(alarm: Alarm)
}