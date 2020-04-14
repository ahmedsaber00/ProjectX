package com.afaqy.ptt.remote.features.login.service

import com.afaqy.ptt.remote.base.response.BaseResponseModel
import com.afaqy.ptt.remote.features.login.model.response.LoginResponseModel
import com.afaqy.ptt.remote.features.login.model.response.SendCodeResponseModel
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String,
        @Field("password") password: String,
        @Field("imei") imei: String,
        @Field("simSerial") simSerial: String): Flowable<LoginResponseModel>

    @FormUrlEncoded
    @POST("password/email")
    fun getVerificationCode(@Field("email") simSerial: String): Flowable<BaseResponseModel>

    @FormUrlEncoded
    @POST("password/verify")
    fun sendVerification(@Field("code") token: String,
                      @Field("simSerial") simSerial: String): Flowable<SendCodeResponseModel>


    @FormUrlEncoded
    @POST("password/reset")
    fun resetPassword(@Field("token") token: String,
                      @Field("simSerial") simSerial: String,
                      @Field("password") password : String,
                      @Field("password_confirmation") password_confirmation : String): Flowable<BaseResponseModel>

}