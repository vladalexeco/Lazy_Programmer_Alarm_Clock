package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.UserStatisticsStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics

class SaveUserStatisticsToDatabaseUseCase(
    private val userStatisticsStorageRepository: UserStatisticsStorageRepository
) {

    suspend operator fun invoke(userStatistics: UserStatistics) {
        userStatisticsStorageRepository.saveUserStatisticsToDatabase(userStatistics)
    }
}