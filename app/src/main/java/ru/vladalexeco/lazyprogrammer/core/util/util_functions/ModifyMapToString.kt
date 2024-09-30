package ru.vladalexeco.lazyprogrammer.core.util.util_functions

fun modifyMapToString(mapOfDays: Map<String, Boolean>): String {
    var result = ""
    var isAllDays = true

    for (day in mapOfDays.keys) {
        if(mapOfDays[day] == true) {
            result += ",$day"
        } else {
            isAllDays = false
        }
    }

    if (result.isNotEmpty()) {
        result = if (result[0] == ',') result.substring(1) else result
    } else {
        result = "Не выбран день"
    }

    return if (isAllDays) "Каждый день" else result
}