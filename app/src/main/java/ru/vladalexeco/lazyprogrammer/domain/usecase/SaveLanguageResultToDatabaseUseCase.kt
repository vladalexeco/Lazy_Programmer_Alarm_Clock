package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.LanguageResultStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult

class SaveLanguageResultToDatabaseUseCase(
    private val languageResultStorageRepository: LanguageResultStorageRepository
) {

    suspend operator fun invoke(languageResult: LanguageResult) {
        languageResultStorageRepository.saveLanguageResultToDatabase(languageResult)
    }
}