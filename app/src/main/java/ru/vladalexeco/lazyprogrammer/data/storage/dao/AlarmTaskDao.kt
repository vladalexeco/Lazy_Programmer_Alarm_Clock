package ru.vladalexeco.lazyprogrammer.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.vladalexeco.lazyprogrammer.data.storage.model.AlarmTaskEntity

@Dao
interface AlarmTaskDao {

    @Insert
    suspend fun insertAlarmTaskEntity(alarmTaskEntity: AlarmTaskEntity)

    @Query("""
        SELECT * FROM task_table 
        WHERE language IN (:language) 
        AND complexity BETWEEN :minComplexity AND :maxComplexity 
        ORDER BY RANDOM() 
        LIMIT :numberOfTasks
    """)
    suspend fun getRandomTasks(
        language: List<String>,
        minComplexity: Int,
        maxComplexity: Int,
        numberOfTasks: Int
    ): List<AlarmTaskEntity>
}