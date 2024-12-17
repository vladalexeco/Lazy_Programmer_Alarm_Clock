package ru.vladalexeco.lazyprogrammer.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.vladalexeco.lazyprogrammer.data.storage.model.UserStatisticsEntity

@Dao
interface UserStatisticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserStatisticsEntity(userStatisticsEntity: UserStatisticsEntity)

    @Query("SELECT * FROM user_statistics WHERE id = :id")
    suspend fun getUserStatisticsEntityById(id: Int): UserStatisticsEntity?
}