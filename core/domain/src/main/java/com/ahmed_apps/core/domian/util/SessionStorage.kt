package com.ahmed_apps.core.domian.util

/**
 * @author Ahmed Guedmioui
 */
interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}