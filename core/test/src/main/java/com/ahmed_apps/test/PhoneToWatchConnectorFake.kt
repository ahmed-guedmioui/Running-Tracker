package com.ahmed_apps.test

import com.ahmed_apps.core.connectivity.domain.DeviceNode
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingAction
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingError
import com.ahmed_apps.core.domian.util.EmptyResult
import com.ahmed_apps.core.domian.util.Result
import com.ahmed_apps.run.domain.WatchConnector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author Ahmed Guedmioui
 */
class PhoneToWatchConnectorFake : WatchConnector {

    var sendError: MessagingError? = null

    private val _isTrackable = MutableStateFlow(false)

    private val _connectedDevices = MutableStateFlow<DeviceNode?>(null)

    private val _messagingActions = MutableSharedFlow<MessagingAction>()


    override val connectedDevices: StateFlow<DeviceNode?>
        get() = _connectedDevices.asStateFlow()

    override val messagingActions: Flow<MessagingAction>
        get() = _messagingActions.asSharedFlow()

    override suspend fun sendActionToWatch(
        action: MessagingAction
    ): EmptyResult<MessagingError> {
        return if (sendError == null) {
            Result.Success(Unit)
        } else {
            Result.Error(sendError!!)
        }
    }

    override fun setIsTrackable(isTrackable: Boolean) {
        this._isTrackable.value = isTrackable
    }

    suspend fun sendFromWatchToPhone(action: MessagingAction) {
        _messagingActions.emit(action)
    }
}