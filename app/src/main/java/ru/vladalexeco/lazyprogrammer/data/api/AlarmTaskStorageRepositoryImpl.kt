package ru.vladalexeco.lazyprogrammer.data.api

import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.data.storage.model.toAlarmTask
import ru.vladalexeco.lazyprogrammer.data.storage.model.toAlarmTaskEntity
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmTaskStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask

class AlarmTaskStorageRepositoryImpl(
    private val appDatabase: AppDatabase
) : AlarmTaskStorageRepository {
    override suspend fun saveAlarmTaskToDatabase(alarmTask: AlarmTask) {
        appDatabase.getAlarmTaskDao().insertAlarmTaskEntity(alarmTaskEntity = alarmTask.toAlarmTaskEntity())
    }

    override suspend fun getRandomAlarmTasksFromDatabase(
        language: List<String>,
        minComplexity: Int,
        maxComplexity: Int,
        numberOfTasks: Int
    ) : List<AlarmTask> {
        val alarmTaskEntityList = appDatabase.getAlarmTaskDao().getRandomTasks(
            language = language,
            minComplexity = minComplexity,
            maxComplexity = maxComplexity,
            numberOfTasks = numberOfTasks
        )

        return alarmTaskEntityList.map { alarmTaskEntity -> alarmTaskEntity.toAlarmTask() }
    }
}