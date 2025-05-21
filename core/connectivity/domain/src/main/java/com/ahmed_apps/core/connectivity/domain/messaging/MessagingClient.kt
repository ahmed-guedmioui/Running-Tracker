package com.ahmed_apps.core.connectivity.domain.messaging

import com.ahmed_apps.core.domian.util.EmptyResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface MessagingClient {
    fun connectToNode(nodeId: String): Flow<MessagingAction>

    suspend fun sendOrQueueAction(action: MessagingAction): EmptyResult<MessagingError>
}