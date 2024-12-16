package ru.vladalexeco.lazyprogrammer.domain.api

import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics

interface UserStatisticsStorageRepository {
    suspend fun saveUserStatisticsToDatabase(userStatistics: UserStatistics)
    suspend fun getUserStatisticsById(id: Int): UserStatistics?
}