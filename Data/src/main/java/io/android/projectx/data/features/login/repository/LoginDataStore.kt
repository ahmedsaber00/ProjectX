package io.android.projectx.data.features.login.repository

import io.android.projectx.data.features.login.model.LoginEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface LoginDataStore {

    fun login(username: String , password:String , imei:String): Flowable<LoginEntity>

    fun clearLogin(): Completable

    fun saveLogin(recipes: LoginEntity): Completable

}