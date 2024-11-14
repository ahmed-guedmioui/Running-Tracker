package com.ahmed_apps.core.domian.run

import com.ahmed_apps.core.domian.util.DataError
import com.ahmed_apps.core.domian.util.EmptyResult
import com.ahmed_apps.core.domian.util.Result

/**
 * @author Ahmed Guedmioui
 */
interface RemoteRunDataSource {
    suspend fun getRuns(): Result<List<Run>, DataError.Network>
    suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network>
    suspend fun deleteRun(id:String): EmptyResult<DataError.Network>
}

















