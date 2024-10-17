package ru.vladalexeco.lazyprogrammer.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vladalexeco.lazyprogrammer.data.api.AlarmStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
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
}