package ru.vladalexeco.lazyprogrammer.domain.api

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStorePreferencesRepository {

    fun <T> getValueFromDataStoreByKey(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T> setValueToDataStoreWithKey(key: Preferences.Key<T>, value: T)
}