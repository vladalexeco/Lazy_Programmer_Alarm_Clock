package ru.vladalexeco.lazyprogrammer.data.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromWeekdaysList(weekdays: List<Boolean>): String {
        val gson = Gson()
        return gson.toJson(weekdays)
    }

    @TypeConverter
    fun toWeekdaysList(data: String): List<Boolean> {
        val gson = Gson()
        val listType = object : TypeToken<List<Boolean>>() {}.type
        return gson.fromJson(data, listType)
    }
}