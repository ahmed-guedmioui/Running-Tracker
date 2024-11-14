package com.ahmed_apps.android_test

import com.ahmed_apps.core.domian.util.AuthInfo
import com.ahmed_apps.core.domian.util.SessionStorage

/**
 * @author Ahmed Guedmioui
 */
class SessionStorageFake: SessionStorage {

    private var authInfo: AuthInfo? = null

    override suspend fun get(): AuthInfo? {
       return authInfo
    }

    override suspend fun set(info: AuthInfo?) {
       authInfo = info
    }
}