package com.ahmed_apps.auth.domain

import com.ahmed_apps.core.domian.util.DataError
import com.ahmed_apps.core.domian.util.EmptyResult

/**
 * @author Ahmed Guedmioui
 */
interface AuthRepository {
    suspend fun register(
        email: String, password: String
    ): EmptyResult<DataError.Network>

    suspend fun login(
        email: String, password: String
    ): EmptyResult<DataError.Network>
}

















