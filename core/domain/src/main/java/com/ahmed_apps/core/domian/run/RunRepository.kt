package com.ahmed_apps.core.domian.run

import com.ahmed_apps.core.domian.util.DataError
import com.ahmed_apps.core.domian.util.EmptyResult
import kotlinx.coroutines.flow.Flow


/**
 * @author Ahmed Guedmioui
 */
interface RunRepository {

    fun getRuns(): Flow<List<Run>>

    suspend fun fetchRuns(): EmptyResult<DataError>

    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError>

    suspend fun deleteAllRuns()

    suspend fun deleteRun(id: RunId)

    suspend fun syncPendingRuns()

    suspend fun logout(): EmptyResult<DataError.Network>
}



















