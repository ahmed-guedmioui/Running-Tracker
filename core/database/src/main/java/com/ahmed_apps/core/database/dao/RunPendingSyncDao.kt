package com.ahmed_apps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ahmed_apps.core.database.entity.DeletedRunSyncEntity
import com.ahmed_apps.core.database.entity.RunPendingSyncEntity

/**
 * @author Ahmed Guedmioui
 */
@Dao
interface RunPendingSyncDao {

    // Created Runs

    @Query("SELECT * FROM runPendingSyncEntity WHERE userId = :userId")
    suspend fun getAllRunPendingSyncEntities(userId: String): List<RunPendingSyncEntity>

    @Query("SELECT * FROM runPendingSyncEntity WHERE runId = :runId")
    suspend fun getRunPendingSyncEntity(runId: String): RunPendingSyncEntity?

    @Upsert
    suspend fun upsertRunPendingSyncEntity(entity: RunPendingSyncEntity)

    @Query("DELETE FROM runPendingSyncEntity WHERE runId = :runId")
    suspend fun deleteRunPendingSyncEntity(runId: String)


    // Deleted Runs

    @Query("SELECT * FROM deletedRunSyncEntity WHERE userId = :userId")
    suspend fun getAllDeletedRunSyncEntities(userId: String): List<DeletedRunSyncEntity>

    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeletedRunSyncEntity)

    @Query("DELETE FROM deletedRunSyncEntity WHERE runId = :runId")
    suspend fun deleteDeletedRunSyncEntity(runId: String)

}




















