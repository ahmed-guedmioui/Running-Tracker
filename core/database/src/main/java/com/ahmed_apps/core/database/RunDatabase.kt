package com.ahmed_apps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmed_apps.core.database.dao.AnalyticsDao
import com.ahmed_apps.core.database.dao.RunDao
import com.ahmed_apps.core.database.dao.RunPendingSyncDao
import com.ahmed_apps.core.database.entity.DeletedRunSyncEntity
import com.ahmed_apps.core.database.entity.RunEntity
import com.ahmed_apps.core.database.entity.RunPendingSyncEntity

/**
 * @author Ahmed Guedmioui
 */
@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
    ],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {
    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
    abstract val analyticsDao: AnalyticsDao
}
















