package com.ahmed_apps.run.data.conctivity

import com.ahmed_apps.core.connectivity.domain.DeviceNode
import com.ahmed_apps.core.connectivity.domain.DeviceType
import com.ahmed_apps.core.connectivity.domain.NodeDiscovery
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingAction
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingClient
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingError
import com.ahmed_apps.core.domian.util.EmptyResult
import com.ahmed_apps.run.domain.WatchConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

/**
 * @author Ahmed Guedmioui
 */
class PhoneToWatchConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope,
    private val messagingClient: MessagingClient
) : WatchConnector {

    private val _connectedNode = MutableStateFlow<DeviceNode?>(null)
    override val connectedDevices = _connectedNode.asStateFlow()

    private val isTrackable = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val messagingActions: Flow<MessagingAction> = nodeDiscovery
        .observeConnectedDevices(DeviceType.PHONE)
        .flatMapLatest { connectedDevices ->
            val node = connectedDevices.firstOrNull()
            if (node != null && node.isNearby) {
                _connectedNode.value = node
                messagingClient.connectToNode(node.id)
            } else emptyFlow()
        }
        .onEach { action ->
            if (action == MessagingAction.ConnectionRequest) {
                if (isTrackable.value) {
                    sendActionToWatch(MessagingAction.Trackable)
                } else {
                    sendActionToWatch(MessagingAction.Untrackable)
                }
            }
        }
        .shareIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly
        )

    init {
        _connectedNode
            .filterNotNull()
            .flatMapLatest { isTrackable }
            .onEach { isTrackable ->
                sendActionToWatch(MessagingAction.ConnectionRequest)
                val action = if (isTrackable) {
                    MessagingAction.Trackable
                } else {
                    MessagingAction.Untrackable
                }
                sendActionToWatch(action)
            }
            .launchIn(applicationScope)
    }

    override suspend fun sendActionToWatch(
        action: MessagingAction
    ): EmptyResult<MessagingError> {
       return messagingClient.sendOrQueueAction(action)
    }

    override fun setIsTrackable(isTrackable: Boolean) {
        this.isTrackable.value = isTrackable
    }
}