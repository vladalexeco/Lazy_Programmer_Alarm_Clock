package ru.vladalexeco.lazyprogrammer.data.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.data.storage.model.toAlarm
import ru.vladalexeco.lazyprogrammer.data.storage.model.toAlarmEntity
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import javax.inject.Inject

class AlarmStorageRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : AlarmStorageRepository {

    override suspend fun saveAlarmToDatabase(alarm: Alarm) {
        appDatabase.getAlarmDao().insertAlarm(alarm.toAlarmEntity())
    }

    override suspend fun getAllAlarmFromDatabase(): Flow<List<Alarm>> = flow {
        val alarmEntities = appDatabase.getAlarmDao().getAllAlarms()
        emit(alarmEntities.map { alarmEntity -> alarmEntity.toAlarm() })
    }

    override suspend fun deleteAlarmFromDatabase(alarm: Alarm) {
        appDatabase.getAlarmDao().deleteAlarm(alarm.toAlarmEntity())
    }
}