package com.afaqy.ptt.data.features.login.repository

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.data.features.login.model.LoginEntity
import com.afaqy.ptt.data.features.login.model.SendCodeEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface LoginDataStore {

    fun getVerificationCode(simSerial:String): Flowable<BaseEntity>

    fun sendVerification(simSerial:String,code:String): Flowable<SendCodeEntity>

    fun login(username: String , password:String , imei:String, simSerial:String): Flowable<LoginEntity>

    fun clearLogin(): Completable

    fun saveLogin(recipes: LoginEntity): Completable

}