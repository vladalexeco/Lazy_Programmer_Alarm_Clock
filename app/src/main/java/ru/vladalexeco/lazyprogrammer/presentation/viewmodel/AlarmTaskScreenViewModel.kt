package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_END_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_START_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_END
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_START
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_TASKS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.LANGUAGE_MAP_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.NUMBER_OF_TASKS_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.buildColoredString
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertStringToMap
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.getListOfMarkedValues
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetRandomAlarmTasksUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetValueFromSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenState
import javax.inject.Inject

@HiltViewModel
class AlarmTaskScreenViewModel @Inject constructor(
    private val getValueFromSharedPreferencesUseCase: GetValueFromSharedPreferencesUseCase,
    private val getRandomAlarmTasksUseCase: GetRandomAlarmTasksUseCase
) : ViewModel() {

    private var listOfAlarmTask: List<AlarmTask> = emptyList()

    private val _uiState = MutableStateFlow(AlarmTaskScreenState())
    val uiState: StateFlow<AlarmTaskScreenState> = _uiState.asStateFlow()

    init {
        val languageMapStringJson = getValueFromSharedPreferencesUseCase(LANGUAGE_MAP_SH_KEY)

        val listOfMarkedValues = getListOfMarkedValues(
            convertStringToMap(
                jsonString = languageMapStringJson,
                defaultKeyValues = supportedProgrammingLanguages
            )
        )

        val complexityStartValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_START_SH_KEY)
            ?: DEFAULT_COMPLEXITY_START

        val complexityEndValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_END_SH_KEY)
            ?: DEFAULT_COMPLEXITY_END

        val numberOfTasksValue = getValueFromSharedPreferencesUseCase(NUMBER_OF_TASKS_SH_KEY)
            ?: DEFAULT_NUMBER_OF_TASKS

        val getTaskFromDatabaseJob = viewModelScope.launch(Dispatchers.IO) {
            listOfAlarmTask = getRandomAlarmTasksUseCase(
                language = listOfMarkedValues.ifEmpty { listOf("kotlin") },
                minComplexity = complexityStartValue.toInt(),
                maxComplexity = complexityEndValue.toInt(),
                numberOfTasks = numberOfTasksValue.toInt()
            )

            _uiState.update { alarmTaskScreenState ->
                alarmTaskScreenState.copy(
                    totalNumberOfTasks = listOfAlarmTask.size
                )
            }

            renderSingleTask()
        }

    }

    fun onEvent(alarmTaskScreenEvent: AlarmTaskScreenEvent) {

    }

    private fun renderSingleTask() {

        if (_uiState.value.taskNumber < _uiState.value.totalNumberOfTasks) {

            val currentAlarmState = listOfAlarmTask[_uiState.value.taskNumber]

            _uiState.update { alarmTaskScreenState ->
                alarmTaskScreenState.copy(
                    taskNumber = _uiState.value.taskNumber + 1,
                    language = currentAlarmState.language,
                    complexity = currentAlarmState.complexity,
                    quest = currentAlarmState.quest,
                    code = buildColoredString(
                        language = currentAlarmState.language,
                        codeText = currentAlarmState.code
                    ),
                    choiceOptions = currentAlarmState.choiceOptions,
                    rightAnswer = currentAlarmState.rightAnswer
                )
            }
        }
    }
}