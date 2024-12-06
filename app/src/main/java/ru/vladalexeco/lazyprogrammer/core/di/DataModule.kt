package ru.vladalexeco.lazyprogrammer.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vladalexeco.lazyprogrammer.data.api.AlarmStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.api.AlarmTaskStorageRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.api.DataStorePreferencesRepositoryImpl
import ru.vladalexeco.lazyprogrammer.data.storage.AppDatabase
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.AlarmTaskStorageRepository
import ru.vladalexeco.lazyprogrammer.domain.api.DataStorePreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {

        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStorePreferencesRepository( dataStore: DataStore<Preferences>): DataStorePreferencesRepository {
        return DataStorePreferencesRepositoryImpl(dataStore = dataStore)
    }
}