package ru.vladalexeco.lazyprogrammer.domain.model

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_ID
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.USER_ID

data class LanguageResult(
    val id: Int = DEFAULT_ID,
    val userStatisticsId: Int = USER_ID,
    val name: String,
    val value: Int = 0
)

