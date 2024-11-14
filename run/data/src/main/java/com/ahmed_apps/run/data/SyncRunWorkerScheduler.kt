package com.ahmed_apps.run.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.ahmed_apps.core.database.dao.RunPendingSyncDao
import com.ahmed_apps.core.database.entity.DeletedRunSyncEntity
import com.ahmed_apps.core.database.entity.RunPendingSyncEntity
import com.ahmed_apps.core.database.mappers.toRunEntity
import com.ahmed_apps.core.domian.run.Run
import com.ahmed_apps.core.domian.run.RunId
import com.ahmed_apps.core.domian.util.SessionStorage
import com.ahmed_apps.run.data.workers.CreateRunWorker
import com.ahmed_apps.run.data.workers.DeleteRunWorker
import com.ahmed_apps.run.data.workers.FetchRunWorker
import com.ahmed_apps.core.domian.run.SyncRunScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * @author Ahmed Guedmioui
 */
class SyncRunWorkerScheduler(
    context: Context,
    private val pendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope
) : SyncRunScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(syncType: SyncRunScheduler.SyncType) {
        when (syncType) {
            is SyncRunScheduler.SyncType.CreateRun -> {
                scheduleCreateRunsWorks(syncType.run, syncType.mapPictureBytes)
            }

            is SyncRunScheduler.SyncType.DeleteRun -> {
                scheduleDeleteRunsWorks(syncType.runId)
            }

            is SyncRunScheduler.SyncType.FetchRuns -> {
                scheduleFetchRunsWorks(syncType.interval)
            }
        }
    }

    private suspend fun scheduleDeleteRunsWorks(runId: RunId) {
        val userId = sessionStorage.get()?.userId ?: return
        val entity = DeletedRunSyncEntity(
            runId = runId,
            userId = userId
        )
        pendingSyncDao.upsertDeletedRunSyncEntity(entity)

        val workRequest = OneTimeWorkRequestBuilder<DeleteRunWorker>()
            .addTag("delete_work")
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = TimeUnit.MINUTES
            )
            .setInputData(
                Data.Builder()
                    .putString(DeleteRunWorker.RUN_ID, entity.runId)
                    .build()
            )
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleCreateRunsWorks(run: Run, mapPictureBytes: ByteArray) {
        val userId = sessionStorage.get()?.userId ?: return
        val pendingRun = RunPendingSyncEntity(
            run = run.toRunEntity(),
            mapPictureBytes = mapPictureBytes,
            userId = userId
        )
        pendingSyncDao.upsertRunPendingSyncEntity(pendingRun)

        val workRequest = OneTimeWorkRequestBuilder<CreateRunWorker>()
            .addTag("create_work")
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = TimeUnit.MINUTES
            )
            .setInputData(
                Data.Builder()
                    .putString(CreateRunWorker.RUN_ID, pendingRun.runId)
                    .build()
            )
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()

    }

    private suspend fun scheduleFetchRunsWorks(interval: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager
                .getWorkInfosByTag("fetch_work")
                .get()
                .isNotEmpty()
        }
        if (isSyncScheduled) return

        val workRequest = PeriodicWorkRequestBuilder<FetchRunWorker>(
            repeatInterval = interval.toJavaDuration()
        )
            .addTag("fetch_work")
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = TimeUnit.MINUTES
            )
            .build()

        workManager.enqueue(workRequest).await()
    }

    override suspend fun cancelAllSyncs() {
        workManager.cancelAllWork().await()
    }

}
