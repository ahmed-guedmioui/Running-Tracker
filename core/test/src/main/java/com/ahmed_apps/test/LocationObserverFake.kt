package com.ahmed_apps.test

import com.ahmed_apps.core.domian.location.LocationWithAltitude
import com.ahmed_apps.run.domain.LocationObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * @author Ahmed Guedmioui
 */
class LocationObserverFake : LocationObserver {

    private val _locations = MutableSharedFlow<LocationWithAltitude>()

    override fun observeLocation(
        interval: Long
    ): Flow<LocationWithAltitude> {
        return _locations.asSharedFlow()
    }

    suspend fun trackLocation(location: LocationWithAltitude) {
        _locations.emit(location)
    }
}