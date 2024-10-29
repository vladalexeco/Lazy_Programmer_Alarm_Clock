package ru.vladalexeco.lazyprogrammer.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vladalexeco.lazyprogrammer.domain.usecase.ScheduleAlarmForDayUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmReceiverEntryPoint {

    fun getScheduleAlarmForDayUseCase(): ScheduleAlarmForDayUseCase
}