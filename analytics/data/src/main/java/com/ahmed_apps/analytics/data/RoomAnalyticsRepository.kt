package com.ahmed_apps.analytics.data

import com.ahmed_apps.analytics.domain.AnalyticsRepository
import com.ahmed_apps.analytics.domain.AnalyticsValues
import com.ahmed_apps.core.database.dao.AnalyticsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration.Companion.microseconds

/**
 * @author Ahmed Guedmioui
 */
class RoomAnalyticsRepository(
    private val analyticsDao: AnalyticsDao
) : AnalyticsRepository {

    override suspend fun getAnalyticsValues(): AnalyticsValues {
        return withContext(Dispatchers.IO) {
            val allRuns = async { analyticsDao.getAllRuns() }
            val totalDistance = async { analyticsDao.getTotalDistanceRun() }
            val totalTimeMillis = async { analyticsDao.getTotalTimeRun() }
            val maxRunSpeed = async { analyticsDao.getGetMaxSpeed() }
            val avgDistancePerRun = async { analyticsDao.getAvgDistancePerRun() }
            val avgPacePerRun = async { analyticsDao.getAvgPacePerRun() }


            AnalyticsValues(
                totalDistanceRun = totalDistance.await(),
                totalTimeRun = totalTimeMillis.await().microseconds,
                fastestEverRun = maxRunSpeed.await(),
                avrDistancePerRun = avgDistancePerRun.await(),
                avrPacePerRun = avgPacePerRun.await(),
                distances = allRuns.await().map { it.distanceMeters }
            )
        }
    }
}

















