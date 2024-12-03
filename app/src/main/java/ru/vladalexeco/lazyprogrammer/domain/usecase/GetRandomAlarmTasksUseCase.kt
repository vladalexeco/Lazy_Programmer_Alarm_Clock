package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.AlarmTaskStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask

class GetRandomAlarmTasksUseCase(
    private val alarmTaskStorageRepository: AlarmTaskStorageRepository
) {
    suspend operator fun invoke(
        language: List<String>,
        minComplexity: Int,
        maxComplexity: Int,
        numberOfTasks: Int
    ): List<AlarmTask> {
        return alarmTaskStorageRepository.getRandomAlarmTasksFromDatabase(
            language = language,
            minComplexity = minComplexity,
            maxComplexity = maxComplexity,
            numberOfTasks = numberOfTasks
        )
    }
}