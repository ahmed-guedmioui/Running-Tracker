package com.ahmed_apps.run.data.workers

import androidx.work.ListenableWorker.Result
import com.ahmed_apps.core.domian.util.DataError

/**
 * @author Ahmed Guedmioui
 */

fun DataError.toWorkerResult(): Result {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> Result.retry()
        DataError.Network.UNAUTHORIZED -> Result.retry()
        DataError.Network.CONFLICT -> Result.retry()
        DataError.Network.TOO_MANY_REQUESTS -> Result.retry()
        DataError.Network.NO_INTERNET -> Result.retry()
        DataError.Network.SERVER_ERROR -> Result.retry()
        DataError.Local.DISK_FULL -> Result.failure()
        DataError.Network.PAYLOAD_TOO_LARGE -> Result.failure()
        DataError.Network.SERIALIZATION_ERROR -> Result.failure()
        DataError.Network.UNKNOWN -> Result.failure()
    }
}









