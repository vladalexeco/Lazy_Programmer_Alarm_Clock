package ru.vladalexeco.lazyprogrammer.presentation.state

import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_USER_STATUS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.resources.ImageVO

data class StatisticsScreenState(
    val numberOfSessions: Int = DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS,
    val numberOfTasks: Int = DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS,
    val numberOfMistakes: Int = DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS,
    val status: String = getStringStatusWithStatusValue(statusValue = DEFAULT_USER_STATUS),
    val emojiStatus: ImageVO = getEmojiStatusWithStatusValue(status = DEFAULT_USER_STATUS),
    val languageResults: Map<String, Int> = getDefaultMapWithInitialParameters(
        supportedProgrammingLanguages
    )
)

fun getDefaultMapWithInitialParameters(
    supportedLanguages: List<String>
): Map<String, Int> {
    val resultMap: MutableMap<String, Int> = linkedMapOf()

    supportedLanguages.forEach { languageName ->
        resultMap[languageName] = 0
    }

    return resultMap
}

fun getStringStatusWithStatusValue(statusValue: Int): String {
    val statusMap: Map<Int, String> = mapOf(
        1 to "Новичек",
        2 to "Любитель",
        3 to "Опытный",
        4 to "Мастер",
        5 to "Профессионал"
    )

    return statusMap[statusValue] ?: ""
}

fun getEmojiStatusWithStatusValue(status: Int): ImageVO {
    val emojiMap: Map<Int, Int> = mapOf(
        1 to R.drawable.newcomer,
        2 to R.drawable.fan,
        3 to R.drawable.skilled,
        4 to R.drawable.master,
        5 to R.drawable.professional
    )

    val emojiResource = emojiMap[status]

    return if (emojiResource != null)
        ImageVO.Resource(emojiResource)
    else
        ImageVO.Resource(R.drawable.newcomer)
}