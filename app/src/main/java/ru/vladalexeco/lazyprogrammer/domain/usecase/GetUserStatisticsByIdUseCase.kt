package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.UserStatisticsStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics

class GetUserStatisticsByIdUseCase(
    private val userStatisticsStorageRepository: UserStatisticsStorageRepository
) {

    suspend operator fun invoke(id: Int): UserStatistics? {
        return userStatisticsStorageRepository.getUserStatisticsById(id)
    }
}