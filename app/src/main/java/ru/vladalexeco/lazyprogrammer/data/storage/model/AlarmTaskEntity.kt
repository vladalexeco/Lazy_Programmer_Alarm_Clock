package ru.vladalexeco.lazyprogrammer.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vladalexeco.lazyprogrammer.domain.model.AlarmTask

@Entity(tableName = "task_table")
data class AlarmTaskEntity(
    @PrimaryKey
    val id: String,
    val quest: String,
    val code: String,
    val choiceOptions: List<String>,
    val rightAnswer: Int,
    val language: String,
    val complexity: Int
)

fun AlarmTaskEntity.toAlarmTask(): AlarmTask {
    return AlarmTask(
        id = this.id,
        quest = this.quest,
        code = this.code,
        choiceOptions = this.choiceOptions,
        rightAnswer = this.rightAnswer,
        language = this.language,
        complexity = this.complexity
    )
}

fun AlarmTask.toAlarmTaskEntity(): AlarmTaskEntity {
    return AlarmTaskEntity(
        id = this.id,
        quest = this.quest,
        code = this.code,
        choiceOptions = this.choiceOptions,
        rightAnswer = this.rightAnswer,
        language = this.language,
        complexity = this.complexity
    )
}

