package ru.vladalexeco.lazyprogrammer.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.vladalexeco.lazyprogrammer.data.storage.model.AlarmEntity

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    @Query("SELECT * FROM alarm_table")
    suspend fun getAllAlarms(): List<AlarmEntity>
}