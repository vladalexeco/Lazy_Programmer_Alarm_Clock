package ru.vladalexeco.lazyprogrammer.domain.model

import ru.vladalexeco.lazyprogrammer.core.util.app_constants.UNDEFINED_VALUE

data class AlarmTask(
    val id: String,
    val quest: String,
    val code: String,
    val choiceOptions: List<String>,
    val rightAnswer: Int,
    val language: String,
    val complexity: Int
) {
    fun hasEmptyFields(): Boolean {
        return (this.quest.isEmpty() ||
                this.code.isEmpty() ||
                this.choiceOptions.any { it.isEmpty() } ||
                this.rightAnswer == UNDEFINED_VALUE ||
                this.language.isEmpty() ||
                this.complexity == UNDEFINED_VALUE)
    }
}
