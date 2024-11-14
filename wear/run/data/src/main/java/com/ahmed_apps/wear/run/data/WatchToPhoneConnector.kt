package com.ahmed_apps.wear.run.data

import com.ahmed_apps.core.connectivity.domain.DeviceNode
import com.ahmed_apps.core.connectivity.domain.DeviceType
import com.ahmed_apps.core.connectivity.domain.NodeDiscovery
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingAction
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingClient
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingError
import com.ahmed_apps.core.domian.util.EmptyResult
import com.ahmed_apps.wear.run.domain.PhoneConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

/**
 * @author Ahmed Guedmioui
 */
class WatchToPhoneConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope,
    private val messagingClient: MessagingClient
) : PhoneConnector {

    private val _connectedNode = MutableStateFlow<DeviceNode?>(null)
    override val connectedNode = _connectedNode.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val messagingActions: Flow<MessagingAction> = nodeDiscovery
        .observeConnectedDevices(DeviceType.WATCH)
        .flatMapLatest { connectedDevices ->
            val node = connectedDevices.firstOrNull()
            if (node != null && node.isNearby) {
                _connectedNode.value = node
                messagingClient.connectToNode(node.id)
            } else flowOf()
        }
        .shareIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly
        )

    override suspend fun sendActionToPhone(
        action: MessagingAction
    ): EmptyResult<MessagingError> {
        return messagingClient.sendOrQueueAction(action)
    }
}