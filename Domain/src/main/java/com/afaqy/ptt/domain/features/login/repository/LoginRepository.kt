package com.afaqy.ptt.domain.features.login.repository

import com.afaqy.ptt.domain.features.login.model.LoginModel
import io.reactivex.Observable

interface LoginRepository {

    fun login(username: String , password:String , imei:String , simSerial:String): Observable<LoginModel>

}