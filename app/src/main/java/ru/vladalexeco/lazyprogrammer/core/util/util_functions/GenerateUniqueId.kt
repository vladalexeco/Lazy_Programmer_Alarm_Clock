package ru.vladalexeco.lazyprogrammer.core.util.util_functions

import java.util.UUID

fun generateUniqueId(): String {
    val uuid = UUID.randomUUID()
    return uuid.toString()
}