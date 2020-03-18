package io.android.projectx.data.features.login.store

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.model.SendCodeEntity
import io.android.projectx.data.features.login.repository.LoginDataStore
import io.android.projectx.data.features.login.repository.LoginRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class LoginRemoteDataStore @Inject constructor(
    private val loginRemote: LoginRemote
) : LoginDataStore {

    override fun login(username: String , password:String , imei:String, simSerial:String): Flowable<LoginEntity> {
        return loginRemote.login(username, password, imei, simSerial)
    }

    override fun getVerificationCode(simSerial:String): Flowable<BaseEntity> {
        return loginRemote.getVerificationCode(simSerial)
    }

    override fun sendVerification(simSerial:String,code : String): Flowable<SendCodeEntity> {
        return loginRemote.sendVerification(simSerial,code)
    }

    override fun clearLogin(): Completable {
        throw UnsupportedOperationException("Clearing Recipes isn't supported here...")
    }

    override fun saveLogin(recipes: LoginEntity): Completable {
        throw UnsupportedOperationException("Saving Recipes isn't supported here...")
    }

}