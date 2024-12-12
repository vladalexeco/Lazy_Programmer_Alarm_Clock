package ru.vladalexeco.lazyprogrammer.presentation.state

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_END
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_START
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_TASKS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.getDefaultMap

data class SettingsScreenState(
    val languageMap: Map<String, Boolean> = getDefaultMap(supportedProgrammingLanguages),
    val complexityStart: String = DEFAULT_COMPLEXITY_START,
    val complexityEnd: String = DEFAULT_COMPLEXITY_END,
    val numberOfTasks: String = DEFAULT_NUMBER_OF_TASKS
)