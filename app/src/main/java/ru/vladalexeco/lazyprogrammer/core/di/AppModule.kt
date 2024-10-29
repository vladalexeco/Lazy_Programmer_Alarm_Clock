package ru.vladalexeco.lazyprogrammer.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.domain.usecase.ScheduleAlarmForDayUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideScheduleAlarmForDayUseCase(
        alarmClockMaker: AlarmClockMaker
    ): ScheduleAlarmForDayUseCase {
        return ScheduleAlarmForDayUseCase(alarmClockMaker = alarmClockMaker)
    }
}