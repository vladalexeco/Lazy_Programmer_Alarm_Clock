package ru.vladalexeco.lazyprogrammer.domain.api

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult

interface LanguageResultStorageRepository {

    suspend fun saveLanguageResultToDatabase(languageResult: LanguageResult)
    suspend fun getAllLanguageResultsByUserStatisticsId(userStatisticsId: Int): Flow<List<LanguageResult>>
    suspend fun getLanguageResultListByLanguageAndUserId(userStatisticsId: Int, languageList: List<String>)
    : Flow<List<LanguageResult>>
}