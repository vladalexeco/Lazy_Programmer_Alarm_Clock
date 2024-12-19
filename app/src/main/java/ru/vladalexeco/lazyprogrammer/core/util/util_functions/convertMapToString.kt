package ru.vladalexeco.lazyprogrammer.core.util.util_functions

import com.google.gson.Gson

fun convertMapToString(map: Map<String, Boolean>): String {
    val gson = Gson()
    return gson.toJson(map)
}

fun convertStringToMap(jsonString: String?, defaultKeyValues: List<String>): Map<String, Boolean> {

    val resultMap: Map<String, Boolean>? = if (jsonString != null) {
        val gson = Gson()
        gson.fromJson(jsonString, Map::class.java) as? Map<String, Boolean>
    } else {
        null
    }

    return resultMap ?: getDefaultMap(defaultKeyValues)
}

fun getDefaultMap(defaultKeyValues: List<String>): Map<String, Boolean> {
    val defaultMap = mutableMapOf<String, Boolean>()
    defaultKeyValues.forEach { item -> defaultMap[item] = false }
    return defaultMap
}

fun getListOfMarkedValues(map: Map<String, Boolean>): List<String> {
    val resultList = mutableListOf<String>()
    map.keys.forEach { key ->
        if (map[key] == true) {
            resultList.add(key)
        }
    }

    return resultList
}