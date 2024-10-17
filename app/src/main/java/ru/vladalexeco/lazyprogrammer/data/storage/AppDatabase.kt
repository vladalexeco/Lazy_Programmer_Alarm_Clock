package ru.vladalexeco.lazyprogrammer.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vladalexeco.lazyprogrammer.data.storage.dao.AlarmDao
import ru.vladalexeco.lazyprogrammer.data.storage.model.AlarmEntity

@Database(version = 1, entities = [AlarmEntity::class])
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao
}