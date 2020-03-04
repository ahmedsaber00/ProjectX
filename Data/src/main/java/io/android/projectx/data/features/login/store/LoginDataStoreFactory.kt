package io.android.projectx.data.features.login.store

import io.android.projectx.data.features.login.repository.LoginDataStore
import javax.inject.Inject

open class LoginDataStoreFactory @Inject constructor(
    private val loginCacheDateStore: LoginCacheDateStore,
    private val loginRemoteDataStore: LoginRemoteDataStore
) {

    open fun getDataStore(loginCached: Boolean, cacheExpired: Boolean): LoginDataStore {
        return if (loginCached && !cacheExpired) loginCacheDateStore
        else loginRemoteDataStore
    }

    open fun getCacheDataStore(): LoginDataStore {
        return loginCacheDateStore
    }

}