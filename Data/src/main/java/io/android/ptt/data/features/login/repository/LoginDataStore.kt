package io.android.ptt.data.features.login.repository

import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.data.features.login.model.LoginEntity
import io.android.ptt.data.features.login.model.SendCodeEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface LoginDataStore {

    fun getVerificationCode(simSerial:String): Flowable<BaseEntity>

    fun sendVerification(simSerial:String,code:String): Flowable<SendCodeEntity>

    fun login(username: String , password:String , imei:String, simSerial:String): Flowable<LoginEntity>

    fun clearLogin(): Completable

    fun saveLogin(recipes: LoginEntity): Completable

}