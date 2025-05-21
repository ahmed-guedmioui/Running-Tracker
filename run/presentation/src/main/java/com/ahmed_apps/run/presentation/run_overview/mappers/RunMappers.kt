package com.ahmed_apps.run.presentation.run_overview.mappers

import com.ahmed_apps.core.domian.run.Run
import com.ahmed_apps.core.presentation.ui.formatted
import com.ahmed_apps.core.presentation.ui.toFormattedHeartRate
import com.ahmed_apps.core.presentation.ui.toFormattedKm
import com.ahmed_apps.core.presentation.ui.toFormattedKmh
import com.ahmed_apps.core.presentation.ui.toFormattedMeters
import com.ahmed_apps.core.presentation.ui.toFormattedPace
import com.ahmed_apps.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * @author Ahmed Guedmioui
 */

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MM, dd, yyyy - HH:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl,
        avgHeartRate = avgHeartRate.toFormattedHeartRate(),
        maxHeartRate = maxHeartRate.toFormattedHeartRate()
    )
}



















