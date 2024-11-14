package com.ahmed_apps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ahmed_apps.core.database.entity.RunEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
@Dao
interface RunDao {
    @Upsert
    suspend fun upsertRun(run: RunEntity)

    @Upsert
    suspend fun upsertRuns(runs: List<RunEntity>)

    @Query("SELECT * FROM runEntity ORDER BY dateTimeUtc DESC")
    fun getRuns(): Flow<List<RunEntity>>

    @Query("DELETE FROM runEntity WHERE id = :id")
    suspend fun deleteRun(id: String)

    @Query("DELETE FROM runEntity")
    suspend fun deleteAllRuns()
}













