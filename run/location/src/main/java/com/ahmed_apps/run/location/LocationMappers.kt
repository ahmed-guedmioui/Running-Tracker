package com.ahmed_apps.run.location

import android.location.Location
import com.ahmed_apps.core.domian.location.LocationWithAltitude

/**
 * @author Ahmed Guedmioui
 */

fun Location.toLocationWithAltitude() : LocationWithAltitude {
    return LocationWithAltitude(
        location = com.ahmed_apps.core.domian.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}














