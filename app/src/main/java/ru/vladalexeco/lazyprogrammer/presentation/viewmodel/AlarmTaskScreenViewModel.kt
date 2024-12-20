package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmSoundPlayer
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_END_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_START_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_END
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_COMPLEXITY_START
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_TASKS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.LANGUAGE_MAP_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.NUMBER_OF_TASKS_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.USER_ID
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.buildColoredString
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertStringToMap
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.getListOfMarkedValues
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetLanguageResultListByLanguageAndUserIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetRandomAlarmTasksUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetUserStatisticsByIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetValueFromSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveLanguageResultToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveUserStatisticsToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenSideEffect
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmTaskScreenState
import ru.vladalexeco.lazyprogrammer.presentation.state.ProcessTaskState
import ru.vladalexeco.lazyprogrammer.presentation.state.getCountMapFromList
import javax.inject.Inject

@HiltViewModel
class AlarmTaskScreenViewModel @Inject constructor(
    private val getValueFromSharedPreferencesUseCase: GetValueFromSharedPreferencesUseCase,
    private val getRandomAlarmTasksUseCase: GetRandomAlarmTasksUseCase,
    private val getLanguageResultListByLanguageAndUserIdUseCase: GetLanguageResultListByLanguageAndUserIdUseCase,
    private val saveLanguageResultToDatabaseUseCase: SaveLanguageResultToDatabaseUseCase,
    private val getUserStatisticsByIdUseCase: GetUserStatisticsByIdUseCase,
    private val saveUserStatisticsToDatabaseUseCase: SaveUserStatisticsToDatabaseUseCase
) : ViewModel() {

    private var listOfAlarmTask: List<AlarmTask> = emptyList()
    private var processTaskState = ProcessTaskState()

    private val _uiState = MutableStateFlow(AlarmTaskScreenState())
    val uiState: StateFlow<AlarmTaskScreenState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<AlarmTaskScreenSideEffect>()
    val sideEffect: SharedFlow<AlarmTaskScreenSideEffect> = _sideEffect.asSharedFlow()

    init {

        // Get User Settings From Shared Preferences
        val languageMapStringJson = getValueFromSharedPreferencesUseCase(LANGUAGE_MAP_SH_KEY)

        val listOfMarkedValues = getListOfMarkedValues(
            convertStringToMap(
                jsonString = languageMapStringJson,
                defaultKeyValues = supportedProgrammingLanguages
            )
        )

        processTaskState.countLanguageMap = getCountMapFromList(listOfMarkedValues)

        val complexityStartValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_START_SH_KEY)
            ?: DEFAULT_COMPLEXITY_START

        val complexityEndValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_END_SH_KEY)
            ?: DEFAULT_COMPLEXITY_END

        val numberOfTasksValue = getValueFromSharedPreferencesUseCase(NUMBER_OF_TASKS_SH_KEY)
            ?: DEFAULT_NUMBER_OF_TASKS
        //

        viewModelScope.launch(Dispatchers.IO) {

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

        when (alarmTaskScreenEvent) {
            is AlarmTaskScreenEvent.ProcessUserTaskResponse -> {
                processUserTaskResponse(isCorrectedAnswer = alarmTaskScreenEvent.isCorrectAnswer)
            }

            AlarmTaskScreenEvent.RenderNewTaskOrCompleteSession -> {
                renderSingleTask()
            }

            AlarmTaskScreenEvent.ProcessWithEmergencyCompletionWithTask -> {
                processWithCompletionOfTask(isInterrupted = true)
            }
        }
    }

    private fun processWithCompletionOfTask(isInterrupted: Boolean) {
        viewModelScope.launch {
            saveLanguageResultsToDatabase()
            saveTaskResultsToDatabase(isInterrupted = isInterrupted)
            goToAnotherScreen()
        }
    }

    private fun processUserTaskResponse(isCorrectedAnswer: Boolean) {

        processTaskState.currentNumberOfTasks.plus(1)

        if (isCorrectedAnswer) {
            processTaskState.countLanguageMap[_uiState.value.language]?.plus(1)
        } else {
            processTaskState.currentNumberOfMistakes.plus(1)
        }

        _uiState.update { alarmTaskScreenState ->
            alarmTaskScreenState.copy(
                answerIsSelected = true
            )
        }
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
                    rightAnswer = currentAlarmState.rightAnswer,
                    answerIsSelected = false
                )
            }
        } else {
            processWithCompletionOfTask(isInterrupted = false)
        }
    }

    private suspend fun goToAnotherScreen() {
        AlarmSoundPlayer.stop()
        _sideEffect.emit(AlarmTaskScreenSideEffect.GoToAnotherScreen)
    }

    private suspend fun saveTaskResultsToDatabase(isInterrupted: Boolean) {
        val userStatistics = getUserStatisticsByIdUseCase(id = USER_ID)

        val additionalValue = if (isInterrupted) 0 else 1

        if (userStatistics != null) {
            val newUserStatistics = userStatistics.copy(
                numberOfSessions = userStatistics.numberOfSessions + additionalValue,
                numberOfMistakes = userStatistics.numberOfMistakes + processTaskState.currentNumberOfMistakes,
                numberOfTasks = userStatistics.numberOfTasks + processTaskState.currentNumberOfTasks,
                status = userStatistics.status + additionalValue
            )

            saveUserStatisticsToDatabaseUseCase(newUserStatistics)
        }
    }

    private suspend fun saveLanguageResultsToDatabase() {
        val languageRequestList: MutableList<String> = mutableListOf()

        val languageMap = processTaskState.countLanguageMap

        languageMap.keys.forEach { key ->
            if (languageMap[key]!! > 0) {
                languageRequestList.add(key)
            }
        }

        if (languageRequestList.isNotEmpty()) {

            getLanguageResultListByLanguageAndUserIdUseCase(
                userStatisticsId = USER_ID,
                languageList = languageRequestList
            ).collect { languageResultList ->

                languageResultList.forEach { languageResult ->

                    val newCount = languageMap[languageResult.name] ?: 0

                    val newLanguageResult = languageResult.copy(
                        value = languageResult.value + newCount
                    )

                    saveLanguageResultToDatabaseUseCase(newLanguageResult)
                }
            }
        }
    }
}