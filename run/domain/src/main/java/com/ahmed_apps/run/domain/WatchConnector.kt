package com.ahmed_apps.run.domain

import com.ahmed_apps.core.connectivity.domain.DeviceNode
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingAction
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingError
import com.ahmed_apps.core.domian.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Ahmed Guedmioui
 */
interface WatchConnector {
    val connectedDevices: StateFlow<DeviceNode?>
    val messagingActions: Flow<MessagingAction>

    suspend fun sendActionToWatch(action: MessagingAction): EmptyResult<MessagingError>

    fun setIsTrackable(isTrackable: Boolean)
}