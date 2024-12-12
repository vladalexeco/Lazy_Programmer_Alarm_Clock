package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_END_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_START_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_END
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_START
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_TASKS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.LANGUAGE_MAP_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.NUMBER_OF_TASKS_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertMapToString
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertStringToMap
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetValueFromSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveValueToSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.SettingsScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.SettingsScreenState
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val getValueFromSharedPreferencesUseCase: GetValueFromSharedPreferencesUseCase,
    private val saveValueToSharedPreferencesUseCase: SaveValueToSharedPreferencesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsScreenState())
    val uiState: StateFlow<SettingsScreenState> = _uiState.asStateFlow()

    init {

        val languageMapStringJson = getValueFromSharedPreferencesUseCase(LANGUAGE_MAP_SH_KEY)

        val languageMapValue = convertStringToMap(
            jsonString = languageMapStringJson,
            defaultKeyValues = supportedProgrammingLanguages
        )

        val complexityStartValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_START_SH_KEY)
            ?: DEFAULT_COMPLEXITY_START

        val complexityEndValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_END_SH_KEY)
            ?: DEFAULT_COMPLEXITY_END

        val numberOfTasksValue = getValueFromSharedPreferencesUseCase(NUMBER_OF_TASKS_SH_KEY)
            ?: DEFAULT_NUMBER_OF_TASKS

        _uiState.update { settingsScreenState ->
            settingsScreenState.copy(
                languageMap = languageMapValue,
                complexityStart = complexityStartValue,
                complexityEnd = complexityEndValue,
                numberOfTasks = numberOfTasksValue
            )
        }
    }

    fun onEvent(settingsScreenEvent: SettingsScreenEvent) {

        when (settingsScreenEvent) {
            is SettingsScreenEvent.ChangeValueOnLanguageMap -> {
                changeValueOnLanguageMap(settingsScreenEvent.key, settingsScreenEvent.value)
            }
            is SettingsScreenEvent.ChangeComplexityValueRange -> {
                changeComplexityValueRange(settingsScreenEvent.start, settingsScreenEvent.end)
            }
            is SettingsScreenEvent.ChangeNumberOfTasksValue -> {
                changeNumberOfTasksValue(settingsScreenEvent.value)
            }
        }
    }

    private fun changeValueOnLanguageMap(key: String, value: Boolean) {
        val updatedLanguageMap = _uiState.value.languageMap.toMutableMap()
        updatedLanguageMap[key] =value

        _uiState.update { settingsScreenState ->
            settingsScreenState.copy(
                languageMap = updatedLanguageMap
            )
        }

        saveValueToSharedPreferencesUseCase(
            key = LANGUAGE_MAP_SH_KEY,
            value = convertMapToString(updatedLanguageMap)
        )
    }

    private fun changeComplexityValueRange(start: Int, end: Int) {
        _uiState.update { settingsScreenState ->
            settingsScreenState.copy(
                complexityStart = start.toString(),
                complexityEnd = end.toString()
            )
        }

        saveValueToSharedPreferencesUseCase(
            key = COMPLEXITY_START_SH_KEY,
            value = start.toString()
        )

        saveValueToSharedPreferencesUseCase(
            key = COMPLEXITY_END_SH_KEY,
            value = end.toString()
        )
    }

    private fun changeNumberOfTasksValue(value: Int) {
        _uiState.update { settingsScreenState ->
            settingsScreenState.copy(
                numberOfTasks = value.toString()
            )
        }

        saveValueToSharedPreferencesUseCase(
            key = NUMBER_OF_TASKS_SH_KEY,
            value = value.toString()
        )
    }
}