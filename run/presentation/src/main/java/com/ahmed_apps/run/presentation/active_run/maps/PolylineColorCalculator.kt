package com.ahmed_apps.run.presentation.active_run.maps

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import com.ahmed_apps.core.domian.location.LocationTimestamp
import kotlin.math.abs

/**
 * @author Ahmed Guedmioui
 */
object PolylineColorCalculator {

    fun locationsToColor(
        location1: LocationTimestamp,
        location2: LocationTimestamp,
    ): Color {
        val distanceMeters = location1.locationWithAltitude.location.distanceTo(location2.locationWithAltitude.location)
        val timeDiff = abs((location2.durationTimestamp - location1.durationTimestamp).inWholeSeconds)
        val speedKm = (distanceMeters / timeDiff) * 3.6

        return interpolateColor(
            speedKm = speedKm,
            minSpeed = 5.0,
            maxSpeed = 20.0,
            colorStart = Color.Green,
            colorMid = Color.Yellow,
            colorEnd = Color.Red
        )
    }

    private fun interpolateColor(
        speedKm: Double,
        minSpeed: Double,
        maxSpeed: Double,
        colorStart: Color,
        colorMid: Color,
        colorEnd: Color,
    ): Color {
        val ratio = ((speedKm - minSpeed) / (maxSpeed - minSpeed)).coerceIn(0.0..1.0)
        val colorInt = if (ratio <= 0.5) {
            val midRatio = ratio / 0.5
            ColorUtils.blendARGB(colorStart.toArgb(), colorMid.toArgb(), midRatio.toFloat())
        } else {
            val midToEndRatio = (ratio - 0.5) / 0.5
            ColorUtils.blendARGB(colorMid.toArgb(), colorEnd.toArgb(), midToEndRatio.toFloat())
        }

        return Color(colorInt)
    }

}

















