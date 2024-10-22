package ru.vladalexeco.lazyprogrammer.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetAllAlarmsFromDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmToDatabaseUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllAlarmsFromDatabaseUseCase(
        alarmStorageRepository: AlarmStorageRepository
    ): GetAllAlarmsFromDatabaseUseCase {
        return GetAllAlarmsFromDatabaseUseCase(alarmStorageRepository = alarmStorageRepository)
    }

    @Provides
    fun provideSaveAlarmToDatabaseUseCase(
        alarmStorageRepository: AlarmStorageRepository
    ): SaveAlarmToDatabaseUseCase {
        return SaveAlarmToDatabaseUseCase(alarmStorageRepository = alarmStorageRepository)
    }
}