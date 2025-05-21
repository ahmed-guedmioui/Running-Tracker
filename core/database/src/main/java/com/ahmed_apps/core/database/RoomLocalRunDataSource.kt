package com.ahmed_apps.core.database

import android.database.sqlite.SQLiteFullException
import com.ahmed_apps.core.database.dao.RunDao
import com.ahmed_apps.core.database.mappers.toRun
import com.ahmed_apps.core.database.mappers.toRunEntity
import com.ahmed_apps.core.domian.run.LocalRunDataSource
import com.ahmed_apps.core.domian.run.Run
import com.ahmed_apps.core.domian.run.RunId
import com.ahmed_apps.core.domian.util.DataError
import com.ahmed_apps.core.domian.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Ahmed Guedmioui
 */
class RoomLocalRunDataSource(
    private val runDao: RunDao
) : LocalRunDataSource {
    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns().map { runEntities ->
            runEntities.map { it.toRun() }
        }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val runEntity = run.toRunEntity()
            runDao.upsertRun(runEntity)
            Result.Success(runEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val runEntities = runs.map { it.toRunEntity() }
            runDao.upsertRuns(runEntities)
            Result.Success(runEntities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}