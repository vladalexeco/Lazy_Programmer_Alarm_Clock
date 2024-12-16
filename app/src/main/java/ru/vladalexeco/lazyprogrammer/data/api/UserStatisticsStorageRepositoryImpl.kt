package ru.vladalexeco.lazyprogrammer.data.api

import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.data.storage.model.toUserStatistics
import ru.vladalexeco.lazyprogrammer.data.storage.model.toUserStatisticsEntity
import ru.vladalexeco.lazyprogrammer.domain.api.UserStatisticsStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics

class UserStatisticsStorageRepositoryImpl(
    private val appDatabase: AppDatabase
) : UserStatisticsStorageRepository {
    override suspend fun saveUserStatisticsToDatabase(userStatistics: UserStatistics) {
        appDatabase.getUserStatisticsDao().insertUserStatisticsEntity(userStatistics.toUserStatisticsEntity())
    }

    override suspend fun getUserStatisticsById(id: Int): UserStatistics? {
        val userStatisticsEntity = appDatabase.getUserStatisticsDao().getUserStatisticsEntityById(id)
        return userStatisticsEntity?.toUserStatistics()
    }
}