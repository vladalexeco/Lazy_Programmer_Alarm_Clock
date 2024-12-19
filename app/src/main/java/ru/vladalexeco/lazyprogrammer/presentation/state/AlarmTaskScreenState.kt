package ru.vladalexeco.lazyprogrammer.presentation.state

import androidx.compose.ui.text.AnnotatedString
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.EMPTY_STRING

data class AlarmTaskScreenState(
    val taskNumber: Int = 0,
    val totalNumberOfTasks: Int = 0,
    val language: String = EMPTY_STRING,
    val complexity: Int = 0,
    val quest: String = EMPTY_STRING,
    val code: AnnotatedString = AnnotatedString(""),
    val choiceOptions: List<String> = listOf("", "", "", ""),
    val rightAnswer: Int = 0,
    val answerIsSelected: Boolean = false
)