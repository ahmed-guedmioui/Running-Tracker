package com.ahmed_apps.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Ahmed Guedmioui
 */
@Entity
class DeletedRunSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val runId: String,
    val userId: String,
)