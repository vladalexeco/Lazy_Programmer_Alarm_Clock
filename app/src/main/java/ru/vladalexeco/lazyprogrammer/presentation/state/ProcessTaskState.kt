package ru.vladalexeco.lazyprogrammer.presentation.state

data class ProcessTaskState(
    var currentNumberOfTasks: Int = 0,
    var currentNumberOfMistakes: Int = 0,
    var countLanguageMap: MutableMap<String, Int> = mutableMapOf()
)

fun getCountMapFromList(list: List<String>): MutableMap<String, Int> {
    val resultMap = linkedMapOf<String, Int>()

    list.forEach { key -> resultMap[key] = 0 }

    return resultMap
}