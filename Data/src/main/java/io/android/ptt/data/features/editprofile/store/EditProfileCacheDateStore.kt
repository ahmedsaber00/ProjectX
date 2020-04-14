package io.android.ptt.data.features.editprofile.store

import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.data.features.login.model.LoginEntity
import io.android.ptt.data.features.login.model.SendCodeEntity
import io.android.ptt.data.features.login.repository.LoginCache
import io.android.ptt.data.features.login.repository.LoginDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class EditProfileCacheDateStore @Inject constructor(
    private val loginCache: LoginCache
) : LoginDataStore {
    override fun login(username: String, password: String, imei: String, simSerial: String): Flowable<LoginEntity> {
        return loginCache.login()
    }
    override fun getVerificationCode(simSerial: String): Flowable<BaseEntity> {
        throw UnsupportedOperationException("Clearing Recipes isn't supported here...")
    }

    override fun sendVerification(simSerial: String, code: String): Flowable<SendCodeEntity> {
        throw UnsupportedOperationException("Clearing Recipes isn't supported here...")
    }

    override fun clearLogin(): Completable {
        return loginCache.clearLogin()
    }

    override fun saveLogin(loginEntity: LoginEntity): Completable {
        return loginCache.saveLogin(loginEntity)
            .andThen(loginCache.setLastCacheTime(System.currentTimeMillis()))
    }

}