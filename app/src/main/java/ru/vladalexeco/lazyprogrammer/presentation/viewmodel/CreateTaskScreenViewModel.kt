package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmTaskToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenSideEffect
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenState
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val saveAlarmTaskToDatabaseUseCase: SaveAlarmTaskToDatabaseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskScreenState())
    val uiState: StateFlow<CreateTaskScreenState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CreateTaskScreenSideEffect>()
    val sideEffect: SharedFlow<CreateTaskScreenSideEffect> = _sideEffect

    fun onEvent(createTaskScreenEvent: CreateTaskScreenEvent) {

        when (createTaskScreenEvent) {

            is CreateTaskScreenEvent.SaveAlarmTaskToDatabase -> {
                saveAlarmTaskToDatabase(createTaskScreenEvent.alarmTask)
            }

            is CreateTaskScreenEvent.SaveAnswerOptionsCurrentValueData -> {
                saveAnswerOptionsCurrentDataValue(createTaskScreenEvent.answerOptionCurrentValue)
            }

            is CreateTaskScreenEvent.SaveComplexityData -> {
                saveComplexityDataOnScreenState(createTaskScreenEvent.complexity)
            }

            is CreateTaskScreenEvent.SaveLanguageData -> {
                saveLanguageDataOnScreenState(createTaskScreenEvent.language)
            }

            is CreateTaskScreenEvent.SaveNumberOfAnswersList -> {
                saveNumberOfAnswersList(createTaskScreenEvent.numberOfAnswersList)
            }

            is CreateTaskScreenEvent.SaveTaskCodeData -> {
                saveTaskCodeDataOnScreenState(createTaskScreenEvent.taskCode)
            }

            is CreateTaskScreenEvent.SaveTaskQuestionData -> {
                saveTaskQuestionDataOnScreenState(createTaskScreenEvent.taskQuestion)
            }

            CreateTaskScreenEvent.ResetAllFieldsToTheirDefaultValues -> {
                resetAllFieldsToTheirDefaultValues()
            }

            is CreateTaskScreenEvent.SaveNumberOfAnswersData -> {
                saveNumberOfAnswersDataOnScreenState(createTaskScreenEvent.numberOfAnswers)
            }
        }
    }

    private fun saveLanguageDataOnScreenState(newLanguageData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(language = newLanguageData)
        }
    }

    private fun saveComplexityDataOnScreenState(newComplexityData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(complexity = newComplexityData)
        }
    }

    private fun saveTaskQuestionDataOnScreenState(newTaskQuestionData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(taskQuestion = newTaskQuestionData)
        }
    }

    private fun saveTaskCodeDataOnScreenState(newTaskCodeData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(taskCode = newTaskCodeData)
        }
    }

    private fun saveNumberOfAnswersDataOnScreenState(newNumberOfAnswers: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                numberOfAnswers = newNumberOfAnswers.toInt(),
                answerOptions = List(newNumberOfAnswers.toInt()) { (it + 1).toString() },
                answersList = List(newNumberOfAnswers.toInt()) { "" },
                answerOptionsCurrentValue = ""
            )
        }
    }

    private fun saveNumberOfAnswersList(newAnswersList: List<String>) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                answersList = newAnswersList
            )
        }
    }

    private fun saveAnswerOptionsCurrentDataValue(newAnswerOptionValue: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                answerOptionsCurrentValue = newAnswerOptionValue
            )
        }
    }

    private fun resetAllFieldsToTheirDefaultValues() {
        _uiState.value = CreateTaskScreenState()
    }

    private fun saveAlarmTaskToDatabase(alarmTask: AlarmTask) {
        if (
            _uiState.value.language.isEmpty() ||
            _uiState.value.complexity.isEmpty() ||
            _uiState.value.taskQuestion.isEmpty() ||
            _uiState.value.taskCode.isEmpty() ||
            _uiState.value.answerOptionsCurrentValue.isEmpty() ||
            _uiState.value.answersList.any { it.isEmpty() }
        ) {
            viewModelScope.launch(context = Dispatchers.Main) {
                _sideEffect.emit(
                    CreateTaskScreenSideEffect.ShowMessage(
                        message = "Заполните все поля"
                    )
                )
            }
        } else {
            viewModelScope.launch {
                saveAlarmTaskToDatabaseUseCase.invoke(alarmTask = alarmTask)
            }
        }
    }
}