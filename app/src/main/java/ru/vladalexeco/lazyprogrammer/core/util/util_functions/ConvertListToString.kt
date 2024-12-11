package ru.vladalexeco.lazyprogrammer.core.util.util_functions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun convertListToString(list: List<String>): String {
    val gson = Gson()
    return gson.toJson(list)
}

fun convertStringToList(data: String): List<String> {
    val gson = Gson()
    val listType = object : TypeToken<List<String>>() {}.type
    return gson.fromJson(data, listType)
}