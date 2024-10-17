package ru.vladalexeco.lazyprogrammer.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hour: String,
    val minute: String,
    val weekdays: List<Boolean>,
    val isActivated: Boolean,
    val melody: String?
)

fun AlarmEntity.toAlarm(): Alarm {
    return Alarm(
        id = this.id,
        hour = this.hour,
        minute = this.minute,
        weekdays = this.weekdays,
        isActivated = this.isActivated,
        melody = this.melody
    )
}

fun Alarm.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = this.id,
        hour = this.hour,
        minute = this.minute,
        weekdays = this.weekdays,
        isActivated = this.isActivated,
        melody = this.melody
    )
}


