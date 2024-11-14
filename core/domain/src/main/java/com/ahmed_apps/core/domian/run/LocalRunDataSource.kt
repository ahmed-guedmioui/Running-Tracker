package com.ahmed_apps.core.domian.run

import com.ahmed_apps.core.domian.util.DataError
import com.ahmed_apps.core.domian.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */

typealias RunId = String

interface LocalRunDataSource {
    fun getRuns(): Flow<List<Run>>

    suspend fun upsertRun(run: Run): Result<RunId, DataError.Local>
    suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local>
    suspend fun deleteRun(id: String)
    suspend fun deleteAllRuns()
}












