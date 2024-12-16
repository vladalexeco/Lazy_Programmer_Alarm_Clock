package ru.vladalexeco.lazyprogrammer.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics

@Entity(tableName = "user_statistics")
data class UserStatisticsEntity(
    @PrimaryKey
    val id: Int,
    val numberOfSessions: Int,
    val numberOfTasks: Int,
    val numberOfMistakes: Int,
    val status: Int
)

fun UserStatisticsEntity.toUserStatistics(): UserStatistics {
    return UserStatistics(
        id = this.id,
        numberOfSessions = this.numberOfSessions,
        numberOfTasks = this.numberOfTasks,
        numberOfMistakes = this.numberOfMistakes,
        status = this.status
    )
}

fun UserStatistics.toUserStatisticsEntity(): UserStatisticsEntity {
    return UserStatisticsEntity(
        id = this.id,
        numberOfSessions = this.numberOfSessions,
        numberOfTasks = this.numberOfTasks,
        numberOfMistakes = this.numberOfMistakes,
        status = this.status
    )
}
