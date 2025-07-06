package ru.vladalexeco.lazyprogrammer.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.vladalexeco.lazyprogrammer.data.storage.model.LanguageResultEntity

@Dao
interface LanguageResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguageResultEntity(languageResultEntity: LanguageResultEntity)

    @Query("SELECT * FROM language_result WHERE userStatisticsId = :userStatisticsId")
    suspend fun getAllLanguageResultByUserStatisticsId(userStatisticsId: Int): List<LanguageResultEntity>

    @Query("SELECT * FROM language_result WHERE userStatisticsId = :userStatisticsId AND name IN (:languageList)")
    suspend fun getLanguageResultListByLanguageAndUserId(userStatisticsId: Int, languageList: List<String>)
    : List<LanguageResultEntity>
}

