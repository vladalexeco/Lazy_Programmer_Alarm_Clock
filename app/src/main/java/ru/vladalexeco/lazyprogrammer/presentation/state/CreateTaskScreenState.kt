package ru.vladalexeco.lazyprogrammer.presentation.state

data class CreateTaskScreenState(
    val language: String,
    val complexity: String,
    val taskQuestion: String,
    val taskCode: String,
    val numberOfAnswers: Int,
    val answerOptions: List<String>,

)