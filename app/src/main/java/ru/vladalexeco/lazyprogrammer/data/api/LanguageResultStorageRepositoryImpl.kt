package ru.vladalexeco.lazyprogrammer.data.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.data.storage.model.toLanguageResult
import ru.vladalexeco.lazyprogrammer.data.storage.model.toLanguageResultEntity
import ru.vladalexeco.lazyprogrammer.domain.api.LanguageResultStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult
import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics

class LanguageResultStorageRepositoryImpl(
    private val appDatabase: AppDatabase
) : LanguageResultStorageRepository {
    override suspend fun saveLanguageResultToDatabase(languageResult: LanguageResult) {
        appDatabase.getLanguageResultDao().insertLanguageResultEntity(
            languageResult.toLanguageResultEntity()
        )
    }

    override suspend fun getAllLanguageResultsByUserStatisticsId(
        userStatisticsId: Int
    ): Flow<List<LanguageResult>> = flow {

        val languageResultEntityList = appDatabase
            .getLanguageResultDao()
            .getAllLanguageResultByUserStatisticsId(userStatisticsId)

        emit(languageResultEntityList.map { languageResultEntity ->
            languageResultEntity.toLanguageResult()
        })
    }

    override suspend fun getLanguageResultListByLanguageAndUserId(
        userStatisticsId: Int,
        languageList: List<String>
    ): Flow<List<LanguageResult>> = flow {

        val languageResultEntityList = appDatabase
            .getLanguageResultDao()
            .getLanguageResultListByLanguageAndUserId(
                userStatisticsId = userStatisticsId,
                languageList = languageList
            )

        emit(languageResultEntityList.map { languageResultEntity ->
            languageResultEntity.toLanguageResult()
        })
    }
}