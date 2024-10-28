package ru.vladalexeco.lazyprogrammer.domain.model

data class Alarm(
    val id: String,
    val hour: String,
    val minute: String,
    val weekdays: List<Boolean>,
    val isExtended: Boolean,
    val isActivated: Boolean,
    val melody: String? = null
)
