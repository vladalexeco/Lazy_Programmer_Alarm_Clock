package ru.vladalexeco.lazyprogrammer.presentation.state

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.DEFAULT_NUMBER_OF_ANSWERS
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.EMPTY_STRING

data class CreateTaskScreenState(
    val language: String = EMPTY_STRING,
    val complexity: String = EMPTY_STRING,
    val taskQuestion: String = EMPTY_STRING,
    val taskCode: String = EMPTY_STRING,
    val numberOfAnswers: String = DEFAULT_NUMBER_OF_ANSWERS,
    val answerOptions: List<String> = List(DEFAULT_NUMBER_OF_ANSWERS.toInt()) { (it + 1).toString() },
    val answerOptionsCurrentValue: String = EMPTY_STRING,
    val answersList: List<String> = List(DEFAULT_NUMBER_OF_ANSWERS.toInt()) { "" },
    val confirmDialogIsVisible: Boolean = false
)