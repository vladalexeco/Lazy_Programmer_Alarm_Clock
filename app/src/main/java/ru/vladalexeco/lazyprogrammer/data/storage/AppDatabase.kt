package ru.vladalexeco.lazyprogrammer.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vladalexeco.lazyprogrammer.data.storage.dao.AlarmDao
import ru.vladalexeco.lazyprogrammer.data.storage.dao.AlarmTaskDao
import ru.vladalexeco.lazyprogrammer.data.storage.dao.LanguageResultDao
import ru.vladalexeco.lazyprogrammer.data.storage.dao.UserStatisticsDao
import ru.vladalexeco.lazyprogrammer.data.storage.model.AlarmEntity
import ru.vladalexeco.lazyprogrammer.data.storage.model.AlarmTaskEntity
import ru.vladalexeco.lazyprogrammer.data.storage.model.LanguageResultEntity
import ru.vladalexeco.lazyprogrammer.data.storage.model.UserStatisticsEntity

@Database(
    version = 1,
    entities = [
        AlarmEntity::class,
        AlarmTaskEntity::class,
        UserStatisticsEntity::class,
        LanguageResultEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao
    abstract fun getAlarmTaskDao(): AlarmTaskDao
    abstract fun getUserStatisticsDao(): UserStatisticsDao
    abstract fun getLanguageResultDao(): LanguageResultDao
}