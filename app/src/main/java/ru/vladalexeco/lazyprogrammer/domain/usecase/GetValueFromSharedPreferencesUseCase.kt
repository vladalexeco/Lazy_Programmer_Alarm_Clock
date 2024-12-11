package ru.vladalexeco.lazyprogrammer.domain.usecase

import ru.vladalexeco.lazyprogrammer.domain.api.SharedPreferencesRepository

class GetValueFromSharedPreferencesUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(key: String): String? {
        return sharedPreferencesRepository.getValueFromSharedPreferences(key)
    }
}