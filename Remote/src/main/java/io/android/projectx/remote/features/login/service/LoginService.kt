package io.android.projectx.remote.features.login.service

import io.android.projectx.remote.features.login.model.response.ForgetPasswordResponseModel
import io.android.projectx.remote.features.login.model.response.LoginResponseModel
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String,
        @Field("password") password: String,
        @Field("imei") imei: String): Flowable<LoginResponseModel>


    @FormUrlEncoded
    @POST("password/phone")
    fun forgetPassword(@Field("countryCode") username: String,
              @Field("mobile") password: String): Flowable<ForgetPasswordResponseModel>
}