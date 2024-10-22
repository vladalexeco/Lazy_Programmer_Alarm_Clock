package ru.vladalexeco.lazyprogrammer.domain.model

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.UNKNOWN_ID
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.generateUniqueId

data class Alarm(
    val id: String,
    val hour: String,
    val minute: String,
    val weekdays: List<Boolean>,
    val isExtended: Boolean,
    val isActivated: Boolean,
    val melody: String? = null
)
