package com.ahmed_apps.run.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.ahmed_apps.core.domian.location.Location
import com.ahmed_apps.core.domian.location.LocationTimestamp
import com.ahmed_apps.core.domian.location.LocationWithAltitude
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

/**
 * @author Ahmed Guedmioui
 */
class LocationDataCalculatorTest {
    private lateinit var locationDataCalculator: LocationDataCalculator

    @BeforeEach
    fun setUp() {
        locationDataCalculator = LocationDataCalculator
    }

    @Test
    fun testIsMaxSpeedCorrect() {
        val startLocation = Location(lat = 40.7128, long = -74.0060)
        val lastLocation = Location(lat = 40.7140, long = -74.0090)

        val locationsWithAltitude = listOf(
            LocationWithAltitude(location = startLocation, altitude = 10.0),
            LocationWithAltitude(location = Location(lat = 40.7130, long = -74.0070), altitude = 12.0),
            LocationWithAltitude(location = Location(lat = 40.7135, long = -74.0080), altitude = 15.0),
            LocationWithAltitude(location = lastLocation, altitude = 18.0)
        )

        val locationTimestamps = listOf(
            LocationTimestamp(locationWithAltitude = locationsWithAltitude[0], durationTimestamp = 0.minutes),
            LocationTimestamp(locationWithAltitude = locationsWithAltitude[1], durationTimestamp = 3.minutes),
            LocationTimestamp(locationWithAltitude = locationsWithAltitude[2], durationTimestamp = 6.minutes),
            LocationTimestamp(locationWithAltitude = locationsWithAltitude[3], durationTimestamp = 9.minutes)
        )

        val hoursDifference =
            (locationTimestamps.last().durationTimestamp - locationTimestamps.first().durationTimestamp)
                .toDouble(DurationUnit.HOURS)
        val distance = startLocation.distanceTo(lastLocation)
        val expectedSpeed = (distance / 1000.0) / hoursDifference

        val maxSpeed = locationDataCalculator.getMaxSpeedKmh(listOf(locationTimestamps))

        assertThat(maxSpeed.roundToInt()).isEqualTo(expectedSpeed.roundToInt())
    }
}

