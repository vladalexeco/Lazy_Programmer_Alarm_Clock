package ru.vladalexeco.lazyprogrammer.core.di

import android.app.AlarmManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMakerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AlarmModule {

    @Provides
    @Singleton
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return alarmManager
    }

    @Provides
    @Singleton
    fun provideAlarmClockMaker(@ApplicationContext context: Context, alarmManager: AlarmManager): AlarmClockMaker {
        return AlarmClockMakerImpl(context = context, alarmManager = alarmManager)
    }
}