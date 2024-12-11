package ru.vladalexeco.lazyprogrammer.domain.api

interface SharedPreferencesRepository {

    fun saveValueToSharedPreferences(key: String, value: String)
    fun getValueFromSharedPreferences(key: String): String?
}