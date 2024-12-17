package ru.vladalexeco.lazyprogrammer.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vladalexeco.lazyprogrammer.data.api.AlarmStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.api.AlarmTaskStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.api.LanguageResultStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.api.SharedPreferencesRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.api.UserStatisticsStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmTaskStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.LanguageResultStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.SharedPreferencesRepository
import ru.vladalexeco.lazyprogrammer.domain.api.UserStatisticsStorageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmStorageRepository(appDatabase: AppDatabase) : AlarmStorageRepository {
        return AlarmStorageRepositoryImpl(appDatabase = appDatabase)
    }

    @Provides
    @Singleton
    fun provideAlarmTaskStorageRepository(appDatabase: AppDatabase): AlarmTaskStorageRepository {
        return AlarmTaskStorageRepositoryImpl(appDatabase = appDatabase)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val sharedPreferences = context.getSharedPreferences("shared_settings", Context.MODE_PRIVATE)
        return sharedPreferences
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(sharedPreferences: SharedPreferences)
    : SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(sharedPreferences = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideUserStatisticsStorageRepository(appDatabase: AppDatabase): UserStatisticsStorageRepository {
        return UserStatisticsStorageRepositoryImpl(appDatabase = appDatabase)
    }

    @Provides
    @Singleton
    fun provideLanguageResultStorageRepository(appDatabase: AppDatabase): LanguageResultStorageRepository {
        return LanguageResultStorageRepositoryImpl(appDatabase = appDatabase)
    }
}