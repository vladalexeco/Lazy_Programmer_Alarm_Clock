package ru.vladalexeco.lazyprogrammer.presentation.state

import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask

sealed interface CreateTaskScreenEvent {
    data class SaveLanguageData(val language: String) : CreateTaskScreenEvent
    data class SaveComplexityData(val complexity: String) : CreateTaskScreenEvent
    data class SaveTaskQuestionData(val taskQuestion: String) : CreateTaskScreenEvent
    data class SaveTaskCodeData(val taskCode: String) : CreateTaskScreenEvent
    data class SaveNumberOfAnswersData(val numberOfAnswers: String) : CreateTaskScreenEvent
    data class SaveNumberOfAnswersList(val numberOfAnswersList: List<String>) : CreateTaskScreenEvent
    data class SaveAnswerOptionsCurrentValueData(val answerOptionCurrentValue: String) : CreateTaskScreenEvent
    data class SaveAlarmTaskToDatabase(val alarmTask: AlarmTask) : CreateTaskScreenEvent
    data object ResetAllFieldsToTheirDefaultValues : CreateTaskScreenEvent
    data class SaveQuestionValueInSharedPreferences(val questionValue: String) : CreateTaskScreenEvent
    data class SaveCodeValueInSharedPreferences(val codeValue: String) : CreateTaskScreenEvent
    data class MakeVisibleDialogBox(val isVisible: Boolean) : CreateTaskScreenEvent
}