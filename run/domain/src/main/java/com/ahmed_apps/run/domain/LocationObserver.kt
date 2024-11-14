package com.ahmed_apps.run.domain

import com.ahmed_apps.core.domian.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow


/**
 * @author Ahmed Guedmioui
 */
interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}