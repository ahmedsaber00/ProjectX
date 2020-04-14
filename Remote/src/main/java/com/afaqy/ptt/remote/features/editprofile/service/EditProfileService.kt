package com.afaqy.ptt.remote.features.editprofile.service

import com.afaqy.ptt.remote.features.login.model.BaseMessageModel
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EditProfileService {

    @Multipart
    @POST("users/profile")
    fun editProfile(
        @Header("Authorization") authorization: String,
        @Part("_method") method: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("ssn") ssn: RequestBody,
        @Part("countryCode") countryCode: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("address") address: RequestBody,
        @Part photo: MultipartBody.Part?
    ): Flowable<BaseMessageModel>

    @Multipart
    @POST("users/profile")
    fun editProfile(
        @Header("Authorization") authorization: String,
        @Part("_method") method: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("ssn") ssn: RequestBody,
        @Part("countryCode") countryCode: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("address") address: RequestBody
    ): Flowable<BaseMessageModel>

}