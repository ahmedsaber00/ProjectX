package io.android.projectx.data.features.login.store

import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.repository.LoginDataStore
import io.android.projectx.data.features.login.repository.LoginRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class LoginRemoteDataStore @Inject constructor(
    private val loginRemote: LoginRemote
) : LoginDataStore {

    override fun login(username: String , password:String , imei:String): Flowable<LoginEntity> {
        return loginRemote.login(username, password, imei)
    }

    override fun clearLogin(): Completable {
        throw UnsupportedOperationException("Clearing Recipes isn't supported here...")
    }

    override fun saveLogin(recipes: LoginEntity): Completable {
        throw UnsupportedOperationException("Saving Recipes isn't supported here...")
    }

}