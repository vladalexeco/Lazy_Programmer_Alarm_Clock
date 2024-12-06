package ru.vladalexeco.lazyprogrammer.data.api

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.vladalexeco.lazyprogrammer.domain.api.DataStorePreferencesRepository

class DataStorePreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStorePreferencesRepository {
    override fun <T> getValueFromDataStoreByKey(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences -> preferences[key] }
    }

    override suspend fun <T> setValueToDataStoreWithKey(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}