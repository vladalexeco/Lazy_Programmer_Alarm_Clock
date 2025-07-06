package ru.vladalexeco.lazyprogrammer.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.vladalexeco.lazyprogrammer.core.alarm.AlarmClockMaker
import ru.vladalexeco.lazyprogrammer.data.api.UserStatisticsStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmTaskStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.LanguageResultStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.SharedPreferencesRepository
import ru.vladalexeco.lazyprogrammer.domain.api.UserStatisticsStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.usecase.CancelAlarmUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.CreateWeeklyAlarmUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.DeleteAlarmFromDatabaseUseSase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetAllAlarmsFromDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetAllLanguageResultsByUserStatisticsIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetLanguageResultListByLanguageAndUserIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetRandomAlarmTasksUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetUserStatisticsByIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetValueFromSharedPreferencesUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmTaskToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveLanguageResultToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveUserStatisticsToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveValueToSharedPreferencesUseCase

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

    @Provides
    fun provideDeleteAlarmFromDatabaseUseCase(
        alarmStorageRepository: AlarmStorageRepository
    ): DeleteAlarmFromDatabaseUseSase {
        return DeleteAlarmFromDatabaseUseSase(alarmStorageRepository = alarmStorageRepository)
    }

    @Provides
    fun provideCreateWeeklyAlarmUseCase(
        alarmClockMaker: AlarmClockMaker
    ): CreateWeeklyAlarmUseCase {
        return CreateWeeklyAlarmUseCase(alarmClockMaker = alarmClockMaker)
    }

    @Provides
    fun provideCancelAlarmUseCase(
        alarmClockMaker: AlarmClockMaker
    ): CancelAlarmUseCase {
        return CancelAlarmUseCase(alarmClockMaker = alarmClockMaker)
    }

    @Provides
    fun provideSaveAlarmTaskToDatabaseUseCase(
        alarmTaskStorageRepository: AlarmTaskStorageRepository
    ): SaveAlarmTaskToDatabaseUseCase {
        return SaveAlarmTaskToDatabaseUseCase(alarmTaskStorageRepository = alarmTaskStorageRepository)
    }

    @Provides
    fun provideGetRandomAlarmTasksUseCase(
        alarmTaskStorageRepository: AlarmTaskStorageRepository
    ): GetRandomAlarmTasksUseCase {
        return GetRandomAlarmTasksUseCase(alarmTaskStorageRepository = alarmTaskStorageRepository)
    }

    @Provides
    fun provideSaveValueToSharedPreferencesUseCase(
        sharedPreferencesRepository: SharedPreferencesRepository
    ): SaveValueToSharedPreferencesUseCase {
        return SaveValueToSharedPreferencesUseCase(sharedPreferencesRepository)
    }

    @Provides
    fun provideGetValueFromSharedPreferencesUseCase(
        sharedPreferencesRepository: SharedPreferencesRepository
    ): GetValueFromSharedPreferencesUseCase {
        return GetValueFromSharedPreferencesUseCase(sharedPreferencesRepository)
    }

    @Provides
    fun provideSaveUserStatisticsToDatabaseUseCase(
        userStatisticsStorageRepository: UserStatisticsStorageRepository
    ): SaveUserStatisticsToDatabaseUseCase {
        return SaveUserStatisticsToDatabaseUseCase(userStatisticsStorageRepository)
    }

    @Provides
    fun provideGetUserStatisticsByIdUseCase(
        userStatisticsStorageRepository: UserStatisticsStorageRepository
    ): GetUserStatisticsByIdUseCase {
        return GetUserStatisticsByIdUseCase(userStatisticsStorageRepository)
    }

    @Provides
    fun provideSaveLanguageResultToDatabaseUseCase(
        languageResultStorageRepository: LanguageResultStorageRepository
    ): SaveLanguageResultToDatabaseUseCase {
        return SaveLanguageResultToDatabaseUseCase(languageResultStorageRepository)
    }

    @Provides
    fun provideGetAllLanguageResultsByUserStatisticsIdUseCase(
        languageResultStorageRepository: LanguageResultStorageRepository
    ): GetAllLanguageResultsByUserStatisticsIdUseCase {
        return GetAllLanguageResultsByUserStatisticsIdUseCase(languageResultStorageRepository)
    }

    @Provides
    fun provideGetLanguageResultListByLanguageAndUserIdUseCase(
        languageResultStorageRepository: LanguageResultStorageRepository
    ): GetLanguageResultListByLanguageAndUserIdUseCase {
        return GetLanguageResultListByLanguageAndUserIdUseCase(languageResultStorageRepository)
    }
}