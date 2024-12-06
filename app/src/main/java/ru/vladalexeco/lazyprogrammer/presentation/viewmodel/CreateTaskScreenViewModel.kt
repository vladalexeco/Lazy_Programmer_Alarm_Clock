package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.datastore.preferences.core.Preferences
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.ANSWER_LIST_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.ANSWER_OPTIONS_CURRENT_VALUE_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.COMPLEXITY_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_ANSWERS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.EMPTY_STRING
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.LANGUAGE_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.NUMBER_OF_ANSWERS_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.TASK_CODE_KEY
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.TASK_QUESTION_KEY
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertListToString
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.convertStringToList
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetValueFromDataStoreByKeyUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmTaskToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SetValueToDataStoreWithKeyUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenSideEffect
import ru.vladalexeco.lazyprogrammer.presentation.state.CreateTaskScreenState
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val saveAlarmTaskToDatabaseUseCase: SaveAlarmTaskToDatabaseUseCase,
    private val getValueFromDataStoreByKeyUseCase: GetValueFromDataStoreByKeyUseCase,
    private val setValueToDataStoreWithKeyUseCase: SetValueToDataStoreWithKeyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskScreenState())
    val uiState: StateFlow<CreateTaskScreenState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CreateTaskScreenSideEffect>()
    val sideEffect: SharedFlow<CreateTaskScreenSideEffect> = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val combinedFirstFive = combine(
                getValueFromDataStoreByKeyUseCase(LANGUAGE_KEY),
                getValueFromDataStoreByKeyUseCase(COMPLEXITY_KEY),
                getValueFromDataStoreByKeyUseCase(TASK_QUESTION_KEY),
                getValueFromDataStoreByKeyUseCase(TASK_CODE_KEY),
                getValueFromDataStoreByKeyUseCase(NUMBER_OF_ANSWERS_KEY),
            ) { languageValue, complexityValue, taskQuestionValue, taskCodeValue,
                numberOfAnswersValue ->

                val numberOfAnswers = numberOfAnswersValue ?: DEFAULT_NUMBER_OF_ANSWERS

                CreateTaskScreenState(
                    language = languageValue ?: EMPTY_STRING,
                    complexity = complexityValue ?: EMPTY_STRING,
                    taskQuestion = taskQuestionValue ?: EMPTY_STRING,
                    taskCode = taskCodeValue ?: EMPTY_STRING,
                    numberOfAnswers = numberOfAnswers,
                    answerOptions = List(numberOfAnswers) { (it + 1).toString() },
                    answerOptionsCurrentValue = EMPTY_STRING,
                    answersList = List(DEFAULT_NUMBER_OF_ANSWERS) { "" }
                )
            }

            combine(
                combinedFirstFive,
                getValueFromDataStoreByKeyUseCase(ANSWER_LIST_KEY),
                getValueFromDataStoreByKeyUseCase(ANSWER_OPTIONS_CURRENT_VALUE_KEY),
            ) { combinedFirstFiveValue, answerListValue, answerOptionsCurrentValue ->

                val answerList =
                    if (answerListValue != null) convertStringToList(answerListValue) else
                        List(combinedFirstFiveValue.numberOfAnswers) { "" }

                val answerOptionsCurrent = answerOptionsCurrentValue ?: EMPTY_STRING

                val newCreateTaskScreenState = combinedFirstFiveValue.copy(
                    answersList = answerList,
                    answerOptionsCurrentValue = answerOptionsCurrent
                )

                newCreateTaskScreenState

            }.collect { newCreateTaskScreenState ->

                withContext(Dispatchers.Main) {
                    _uiState.update {
                        newCreateTaskScreenState
                    }
                }
            }
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

            is CreateTaskScreenEvent.SaveQuestionValueInDataStore -> {
                saveQuestionValueInDataStore(
                    createTaskScreenEvent.questionValue
                )
            }

            is CreateTaskScreenEvent.SaveCodeValueInDataStore -> {
                saveCodeValueInDataStore(
                    createTaskScreenEvent.codeValue
                )
            }
        }
    }

    private fun saveCodeValueInDataStore(codeValue: String) {

        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(key = TASK_CODE_KEY, value = codeValue)

            _sideEffect.emit(
                CreateTaskScreenSideEffect.ShowMessage(
                    message = codeValue
                )
            )
        }


    }

    private fun saveQuestionValueInDataStore(questionValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(key = TASK_QUESTION_KEY, value = questionValue)

            _sideEffect.emit(
                CreateTaskScreenSideEffect.ShowMessage(
                    message = questionValue
                )
            )
        }
    }

    private fun saveLanguageDataOnScreenState(newLanguageData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(language = newLanguageData)
        }

        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(key = LANGUAGE_KEY, value = newLanguageData)
        }
    }

    private fun saveComplexityDataOnScreenState(newComplexityData: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(complexity = newComplexityData)
        }

        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(key = COMPLEXITY_KEY, value = newComplexityData)
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

        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(
                key = NUMBER_OF_ANSWERS_KEY,
                value = newNumberOfAnswers.toInt()
            )

            setValueToDataStoreWithKeyUseCase(
                key = ANSWER_LIST_KEY,
                value = convertListToString(list = List(newNumberOfAnswers.toInt()) { "" })
            )

            setValueToDataStoreWithKeyUseCase(key = ANSWER_OPTIONS_CURRENT_VALUE_KEY, value = "")
        }
    }

    private fun saveNumberOfAnswersList(newAnswersList: List<String>) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                answersList = newAnswersList
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(
                key = ANSWER_LIST_KEY,
                value = convertListToString(newAnswersList)
            )
        }
    }

    private fun saveAnswerOptionsCurrentDataValue(newAnswerOptionValue: String) {
        _uiState.update { createTaskScreenState ->
            createTaskScreenState.copy(
                answerOptionsCurrentValue = newAnswerOptionValue
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            setValueToDataStoreWithKeyUseCase(
                key = ANSWER_OPTIONS_CURRENT_VALUE_KEY,
                value = newAnswerOptionValue
            )
        }
    }

    private fun resetAllFieldsToTheirDefaultValues() {
        _uiState.value = CreateTaskScreenState()

        viewModelScope.launch(Dispatchers.IO) {

            viewModelScope.launch(Dispatchers.IO) {
                setValueToDataStoreWithKeyUseCase(key = LANGUAGE_KEY, value = EMPTY_STRING)
            }

            viewModelScope.launch(Dispatchers.IO) {
                setValueToDataStoreWithKeyUseCase(key = COMPLEXITY_KEY, value = EMPTY_STRING)
            }

            viewModelScope.launch(Dispatchers.IO) {
                setValueToDataStoreWithKeyUseCase(key = TASK_QUESTION_KEY, value = EMPTY_STRING)
            }

            viewModelScope.launch(Dispatchers.IO) {
                setValueToDataStoreWithKeyUseCase(key = TASK_CODE_KEY, value = EMPTY_STRING)
            }

            setValueToDataStoreWithKeyUseCase(
                key = NUMBER_OF_ANSWERS_KEY,
                value = DEFAULT_NUMBER_OF_ANSWERS
            )

            setValueToDataStoreWithKeyUseCase(
                key = ANSWER_LIST_KEY,
                value = convertListToString(list = List(DEFAULT_NUMBER_OF_ANSWERS) { "" })
            )

            setValueToDataStoreWithKeyUseCase(key = ANSWER_OPTIONS_CURRENT_VALUE_KEY, value = "")
        }
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