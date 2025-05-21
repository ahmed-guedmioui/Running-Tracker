package com.ahmed_apps.core.connectivity.domain

import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface NodeDiscovery {
    fun observeConnectedDevices(localDeviceType: DeviceType): Flow<Set<DeviceNode>>
}