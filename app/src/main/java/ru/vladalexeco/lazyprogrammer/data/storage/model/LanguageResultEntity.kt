package ru.vladalexeco.lazyprogrammer.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult

@Entity(tableName = "language_result")
data class LanguageResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userStatisticsId: Int,
    val name: String,
    val value: Int
)

fun LanguageResultEntity.toLanguageResult(): LanguageResult {
    return LanguageResult(
        id = this.id,
        userStatisticsId = this.userStatisticsId,
        name = this.name,
        value = this.value
    )
}

fun LanguageResult.toLanguageResultEntity(): LanguageResultEntity {
    return LanguageResultEntity(
        id = this.id,
        userStatisticsId = this.userStatisticsId,
        name = this.name,
        value = this.value
    )
}

