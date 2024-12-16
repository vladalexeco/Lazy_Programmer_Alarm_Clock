package ru.vladalexeco.lazyprogrammer.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.vladalexeco.lazyprogrammer.data.storage.model.LanguageResultEntity

@Dao
interface LanguageResultDao {

    @Insert
    suspend fun insertLanguageResultEntity(languageResultEntity: LanguageResultEntity)

    @Query("SELECT * FROM language_result WHERE userStatisticsId = :userStatisticsId")
    suspend fun getAllLanguageResultByUserStatisticsId(userStatisticsId: Int): List<LanguageResultEntity>
}

