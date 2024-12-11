package ru.vladalexeco.lazyprogrammer.data.api

import android.content.SharedPreferences
import ru.vladalexeco.lazyprogrammer.domain.api.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

    override fun saveValueToSharedPreferences(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    override fun getValueFromSharedPreferences(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}