package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.SharedPreferencesRepository

class SaveValueToSharedPreferencesUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(key: String, value: String) {
        sharedPreferencesRepository.saveValueToSharedPreferences(key, value)
    }
}