package com.ahmed_apps.auth.data

import com.ahmed_apps.auth.domain.AuthRepository
import com.ahmed_apps.core.data.networking.post
import com.ahmed_apps.core.domian.util.AuthInfo
import com.ahmed_apps.core.domian.util.DataError
import com.ahmed_apps.core.domian.util.EmptyResult
import com.ahmed_apps.core.domian.util.Result
import com.ahmed_apps.core.domian.util.SessionStorage
import com.ahmed_apps.core.domian.util.asEmptyDataResult
import io.ktor.client.HttpClient

/**
 * @author Ahmed Guedmioui
 */
class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {
    override suspend fun register(
        email: String, password: String
    ): EmptyResult<DataError.Network> {

        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )

    }

    override suspend fun login(
        email: String, password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }

        return result.asEmptyDataResult()
    }
}
















