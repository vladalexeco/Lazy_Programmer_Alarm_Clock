package ru.vladalexeco.lazyprogrammer.domain.usecase

import androidx.datastore.preferences.core.Preferences
import ru.vladalexeco.lazyprogrammer.domain.api.DataStorePreferencesRepository

class SetValueToDataStoreWithKeyUseCase(
    private val dataStorePreferencesRepository: DataStorePreferencesRepository
) {

    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) {
        dataStorePreferencesRepository.setValueToDataStoreWithKey(key = key, value = value)
    }
}