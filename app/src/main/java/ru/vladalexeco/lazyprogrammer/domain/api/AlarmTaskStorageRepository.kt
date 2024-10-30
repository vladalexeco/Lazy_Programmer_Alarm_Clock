package ru.vladalexeco.lazyprogrammer.domain.api

import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask

interface AlarmTaskStorageRepository {

    suspend fun saveAlarmTaskToDatabase(alarmTask: AlarmTask)

    suspend fun getRandomAlarmTasksFromDatabase(
        language: List<String>,
        minComplexity: Int,
        maxComplexity: Int,
        numberOfTasks: Int
    ) : List<AlarmTask>
}