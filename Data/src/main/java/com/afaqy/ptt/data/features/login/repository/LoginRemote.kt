package com.afaqy.ptt.data.features.login.repository

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.data.features.login.model.LoginEntity
import com.afaqy.ptt.data.features.login.model.SendCodeEntity
import io.reactivex.Flowable

interface LoginRemote {

    fun login(username: String , password:String , imei:String , simSerial:String): Flowable<LoginEntity>

    fun getVerificationCode(simSerial:String): Flowable<BaseEntity>

    fun sendVerification(simSerial: String,code: String): Flowable<SendCodeEntity>

    fun resetPassword(token: String,simSerial: String,password: String,passwordConfirmation: String): Flowable<BaseEntity>

}