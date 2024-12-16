package ru.vladalexeco.lazyprogrammer.domain.model

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_USER_STATUS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.USER_ID

data class UserStatistics(
    val id: Int = USER_ID,
    val numberOfSessions: Int = DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS,
    val numberOfTasks: Int = DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS,
    val numberOfMistakes: Int = DEFAULT_VALUE_OF_USER_STATISTICS_PARAMETERS,
    val status: Int = DEFAULT_USER_STATUS
)