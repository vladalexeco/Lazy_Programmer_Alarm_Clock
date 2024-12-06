package ru.vladalexeco.lazyprogrammer.domain.usecase

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.lazyprogrammer.domain.api.DataStorePreferencesRepository

class GetValueFromDataStoreByKeyUseCase(
    private val dataStorePreferencesRepository: DataStorePreferencesRepository
) {

    operator fun <T> invoke(key: Preferences.Key<T>): Flow<T?> {
        return dataStorePreferencesRepository.getValueFromDataStoreByKey(key = key)
    }
}