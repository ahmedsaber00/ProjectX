package io.android.projectx.data.features.login.repository

import io.android.projectx.data.features.login.model.LoginEntity
import io.reactivex.Flowable

interface LoginRemote {

    fun login(username: String , password:String , imei:String): Flowable<LoginEntity>

}