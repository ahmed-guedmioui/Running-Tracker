package com.ahmed_apps.core.domian.run

import kotlin.time.Duration

/**
 * @author Ahmed Guedmioui
 */
interface SyncRunScheduler {

    suspend fun scheduleSync(syncType: SyncType)
    suspend fun cancelAllSyncs()

    sealed interface SyncType {
        data class FetchRuns(val interval: Duration): SyncType
        data class DeleteRun(val runId: RunId): SyncType
        data class CreateRun(val run: Run, val mapPictureBytes: ByteArray): SyncType
    }
}














