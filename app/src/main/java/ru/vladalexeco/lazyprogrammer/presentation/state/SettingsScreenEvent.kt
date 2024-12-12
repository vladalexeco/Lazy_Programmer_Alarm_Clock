package ru.vladalexeco.lazyprogrammer.presentation.state

sealed interface SettingsScreenEvent {
    data class ChangeValueOnLanguageMap(val key: String, val value: Boolean) : SettingsScreenEvent
    data class ChangeComplexityValueRange(val start: Int, val end: Int) : SettingsScreenEvent
    data class ChangeNumberOfTasksValue(val value: Int) : SettingsScreenEvent
}