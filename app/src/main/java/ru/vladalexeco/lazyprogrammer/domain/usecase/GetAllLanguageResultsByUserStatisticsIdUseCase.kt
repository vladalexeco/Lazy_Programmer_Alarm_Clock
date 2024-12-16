package ru.vladalexeco.lazyprogrammer.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.lazyprogrammer.domain.api.LanguageResultStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult

class GetAllLanguageResultsByUserStatisticsIdUseCase(
    private val languageResultStorageRepository: LanguageResultStorageRepository
) {

    suspend operator fun invoke(userStatisticsId: Int): Flow<List<LanguageResult>> {
        return languageResultStorageRepository.getAllLanguageResultsByUserStatisticsId(userStatisticsId)
    }
}