package com.ahmed_apps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ahmed_apps.core.database.entity.RunEntity

/**
 * @author Ahmed Guedmioui
 */
@Dao
interface AnalyticsDao {

    @Query("SELECT * FROM runEntity")
    suspend fun getAllRuns(): List<RunEntity>

    @Query("SELECT SUM(distanceMeters) FROM runEntity")
    suspend fun getTotalDistanceRun(): Int

    @Query("SELECT SUM(durationMillis) FROM runEntity")
    suspend fun getTotalTimeRun(): Long

    @Query("SELECT MAX(maxSpeedKmh) FROM runEntity")
    suspend fun getGetMaxSpeed(): Double

    @Query("SELECT AVG(distanceMeters) FROM runEntity")
    suspend fun getAvgDistancePerRun(): Double

    @Query("SELECT AVG((durationMillis / 6000.0) / (distanceMeters / 1000.0)) FROM runEntity")
    suspend fun getAvgPacePerRun(): Double

}










