package io.android.projectx.data.features.login.repository

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.model.SendCodeEntity
import io.reactivex.Flowable

interface LoginRemote {

    fun login(username: String , password:String , imei:String , simSerial:String): Flowable<LoginEntity>

    fun getVerificationCode(simSerial:String): Flowable<BaseEntity>

    fun sendVerification(simSerial: String,code: String): Flowable<SendCodeEntity>

    fun resetPassword(token: String,simSerial: String,password: String,passwordConfirmation: String): Flowable<BaseEntity>

}