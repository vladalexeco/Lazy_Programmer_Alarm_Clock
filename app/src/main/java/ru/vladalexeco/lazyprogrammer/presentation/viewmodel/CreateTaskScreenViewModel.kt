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
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.ANSWER_LIST_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.ANSWER_OPTIONS_CURRENT_VALUE_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_ANSWERS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.EMPTY_STRING
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.LANGUAGE_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.NUMBER_OF_ANSWERS_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.TASK_CODE_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.TASK_QUESTION_SH_KEY
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertListToString
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertStringToList
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetValueFromSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmTaskToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveValueToSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenSideEffect
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenState
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val saveAlarmTaskToDatabaseUseCase: SaveAlarmTaskToDatabaseUseCase,
    private val getValueFromSharedPreferencesUseCase: GetValueFromSharedPreferencesUseCase,
    private val saveValueToSharedPreferencesUseCase: SaveValueToSharedPreferencesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskScreenState())
    val uiState: StateFlow<CreateTaskScreenState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CreateTaskScreenSideEffect>()
    val sideEffect: SharedFlow<CreateTaskScreenSideEffect> = _sideEffect.asSharedFlow()

    init {

        val languageValue = getValueFromSharedPreferencesUseCase(LANGUAGE_SH_KEY) ?: EMPTY_STRING
        val complexityValue = getValueFromSharedPreferencesUseCase(COMPLEXITY_SH_KEY) ?: EMPTY_STRING
        val taskQuestionValue = getValueFromSharedPreferencesUseCase(TASK_QUESTION_SH_KEY) ?: EMPTY_STRING
        val taskCodeValue = getValueFromSharedPreferencesUseCase(TASK_CODE_SH_KEY) ?: EMPTY_STRING
        val numberOfAnswersValue = getValueFromSharedPreferencesUseCase(NUMBER_OF_ANSWERS_SH_KEY) ?: DEFAULT_NUMBER_OF_ANSWERS

        val answerList = getValueFromSharedPreferencesUseCase(ANSWER_LIST_SH_KEY)
        val answerListValue = if (answerList != null) convertStringToList(answerList) else
            List(numberOfAnswersValue.toInt()) { "" }

        val answerOptionCurrentValue =  getValueFromSharedPreferencesUseCase(ANSWER_OPTIONS_CURRENT_VALUE_SH_KEY) ?: EMPTY_STRING

        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                language = languageValue,
                complexity = complexityValue,
                taskQuestion = taskQuestionValue,
                taskCode = taskCodeValue,
                numberOfAnswers = numberOfAnswersValue,
                answerOptions = List(numberOfAnswersValue.toInt()) { (it + 1).toString() },
                answersList = answerListValue,
                answerOptionsCurrentValue = answerOptionCurrentValue
            )
        }
    }

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

            is CreateTaskScreenEvent.SaveQuestionValueInSharedPreferences -> {
                saveQuestionValueInSharedPreferences(
                    createTaskScreenEvent.questionValue
                )
            }

            is CreateTaskScreenEvent.SaveCodeValueInSharedPreferences -> {
                saveCodeValueInSharedPreferences(
                    createTaskScreenEvent.codeValue
                )
            }
        }
    }

    private fun saveCodeValueInSharedPreferences(codeValue: String) {

        saveValueToSharedPreferencesUseCase(key = TASK_CODE_SH_KEY, value = codeValue)
    }

    private fun saveQuestionValueInSharedPreferences(questionValue: String) {

        saveValueToSharedPreferencesUseCase(key = TASK_QUESTION_SH_KEY, value = questionValue)
    }

    private fun saveLanguageDataOnScreenState(newLanguageData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(language = newLanguageData)
        }

        saveValueToSharedPreferencesUseCase(key = LANGUAGE_SH_KEY, value = newLanguageData)
    }

    private fun saveComplexityDataOnScreenState(newComplexityData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(complexity = newComplexityData)
        }

        saveValueToSharedPreferencesUseCase(key = COMPLEXITY_SH_KEY, value = newComplexityData)
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
                numberOfAnswers = newNumberOfAnswers,
                answerOptions = List(newNumberOfAnswers.toInt()) { (it + 1).toString() },
                answersList = List(newNumberOfAnswers.toInt()) { "" },
                answerOptionsCurrentValue = ""
            )
        }

        saveValueToSharedPreferencesUseCase(key = NUMBER_OF_ANSWERS_SH_KEY, value = newNumberOfAnswers)

        saveValueToSharedPreferencesUseCase(
            key = ANSWER_LIST_SH_KEY,
            value = convertListToString(list = List(newNumberOfAnswers.toInt()) { "" })
        )

        saveValueToSharedPreferencesUseCase(
            key = ANSWER_OPTIONS_CURRENT_VALUE_SH_KEY,
            value = ""
        )

    }

    private fun saveNumberOfAnswersList(newAnswersList: List<String>) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                answersList = newAnswersList
            )
        }

        saveValueToSharedPreferencesUseCase(
            key = ANSWER_LIST_SH_KEY,
            value = convertListToString(newAnswersList)
        )
    }

    private fun saveAnswerOptionsCurrentDataValue(newAnswerOptionValue: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                answerOptionsCurrentValue = newAnswerOptionValue
            )
        }

        saveValueToSharedPreferencesUseCase(
            key = ANSWER_OPTIONS_CURRENT_VALUE_SH_KEY,
            value = newAnswerOptionValue
        )
    }

    private fun resetAllFieldsToTheirDefaultValues() {
        _uiState.value = CreateTaskScreenState()

        saveValueToSharedPreferencesUseCase(key = LANGUAGE_SH_KEY, value = EMPTY_STRING)
        saveValueToSharedPreferencesUseCase(key = COMPLEXITY_SH_KEY, value = EMPTY_STRING)
        saveValueToSharedPreferencesUseCase(key = TASK_QUESTION_SH_KEY, value = EMPTY_STRING)
        saveValueToSharedPreferencesUseCase(key = TASK_CODE_SH_KEY, value = EMPTY_STRING)

        saveValueToSharedPreferencesUseCase(
            key = NUMBER_OF_ANSWERS_SH_KEY,
            value = DEFAULT_NUMBER_OF_ANSWERS
        )

        saveValueToSharedPreferencesUseCase(
            key = ANSWER_LIST_SH_KEY,
            value = convertListToString(list = List(DEFAULT_NUMBER_OF_ANSWERS.toInt()) { "" })
        )

        saveValueToSharedPreferencesUseCase(key = ANSWER_OPTIONS_CURRENT_VALUE_SH_KEY, value = "")
    }

    private fun saveAlarmTaskToDatabase(alarmTask: AlarmTask) {

        viewModelScope.launch(context = Dispatchers.Main) {
            if (alarmTask.hasEmptyFields()) {
                _sideEffect.emit(
                    CreateTaskScreenSideEffect.ShowMessage(
                        message = "Заполните все поля"
                    )
                )
            } else {
                saveAlarmTaskToDatabaseUseCase.invoke(alarmTask = alarmTask)

                _sideEffect.emit(
                    CreateTaskScreenSideEffect.ShowMessage(
                        message = "Задание сохранено"
                    )
                )
            }
        }
    }
}