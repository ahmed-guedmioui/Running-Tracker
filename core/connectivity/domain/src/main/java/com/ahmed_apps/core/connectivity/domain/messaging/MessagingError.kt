package com.ahmed_apps.core.connectivity.domain.messaging

import com.ahmed_apps.core.domian.util.Error

/**
 * @author Ahmed Guedmioui
 */
enum class MessagingError: Error {
    CONNECTION_INTERRUPTED,
    DISCONNECTED,
    UNKNOWN
}