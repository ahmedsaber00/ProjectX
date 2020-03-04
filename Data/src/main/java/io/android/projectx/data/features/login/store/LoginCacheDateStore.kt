package io.android.projectx.data.features.login.store

import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.repository.LoginCache
import io.android.projectx.data.features.login.repository.LoginDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class LoginCacheDateStore @Inject constructor(
    private val loginCache: LoginCache
) : LoginDataStore {
    override fun login(username: String, password: String, imei: String): Flowable<LoginEntity> {
        return loginCache.login()
    }

    override fun clearLogin(): Completable {
        return loginCache.clearLogin()
    }

    override fun saveLogin(loginEntity: LoginEntity): Completable {
        return loginCache.saveLogin(loginEntity)
            .andThen(loginCache.setLastCacheTime(System.currentTimeMillis()))
    }

}