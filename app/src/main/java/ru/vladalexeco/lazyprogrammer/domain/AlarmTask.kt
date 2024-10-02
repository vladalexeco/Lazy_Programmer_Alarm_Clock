package ru.vladalexeco.lazyprogrammer.domain

data class AlarmTask(
    val id: Int,
    val quest: String,
    val code: String,
    val choiceOptions: List<String>,
    val rightAnswer: Int,
    val language: String,
    val complexity: Int
)
