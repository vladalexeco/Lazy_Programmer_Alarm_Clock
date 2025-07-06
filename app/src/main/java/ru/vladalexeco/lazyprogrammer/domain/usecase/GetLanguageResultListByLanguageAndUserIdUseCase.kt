package ru.vladalexeco.lazyprogrammer.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.lazyprogrammer.domain.api.LanguageResultStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult

class GetLanguageResultListByLanguageAndUserIdUseCase(
    private val languageResultStorageRepository: LanguageResultStorageRepository
) {

    suspend operator fun invoke(
        userStatisticsId: Int,
        languageList: List<String>
    ): Flow<List<LanguageResult>> {
        return languageResultStorageRepository.getLanguageResultListByLanguageAndUserId(
            userStatisticsId = userStatisticsId,
            languageList = languageList
        )
    }
}