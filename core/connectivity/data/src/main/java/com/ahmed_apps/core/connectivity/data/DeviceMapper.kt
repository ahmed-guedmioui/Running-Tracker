package com.ahmed_apps.core.connectivity.data

import com.ahmed_apps.core.connectivity.domain.DeviceNode
import com.google.android.gms.wearable.Node

/**
 * @author Ahmed Guedmioui
 */
fun Node.toDeviceNode(): DeviceNode {
    return DeviceNode(
        id = id,
        displayName = displayName,
        isNearby = isNearby
    )
}