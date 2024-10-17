package ru.vladalexeco.lazyprogrammer.domain.model

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.UNKNOWN_ID

data class Alarm(
    val id: Int = UNKNOWN_ID,
    val hour: String,
    val minute: String,
    val weekdays: List<Boolean>,
    val isActivated: Boolean,
    val melody: String? = null
)
