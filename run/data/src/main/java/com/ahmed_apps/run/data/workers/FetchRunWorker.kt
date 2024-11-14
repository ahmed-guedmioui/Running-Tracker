package com.ahmed_apps.run.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ahmed_apps.core.domian.run.RunRepository

/**
 * @author Ahmed Guedmioui
 */
class FetchRunWorker(
    context: Context,
    params: WorkerParameters,
    private val runRepository: RunRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        return when (val result = runRepository.fetchRuns()) {
            is com.ahmed_apps.core.domian.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.ahmed_apps.core.domian.util.Result.Success -> {
                Result.success()
            }
        }
    }

}
















