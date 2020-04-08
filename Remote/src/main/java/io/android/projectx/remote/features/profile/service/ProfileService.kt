package io.android.projectx.remote.features.profile.service

import io.android.projectx.remote.base.response.BaseResponseModel
import io.android.projectx.remote.features.profile.model.response.ProfileResponseModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileService {

    @GET("users/profile")
    fun getProfile(@Header("Authorization") authorization: String): Flowable<ProfileResponseModel>

    @POST("channels")
    fun updateProfile(@Header("Authorization") authorization: String, @Query("page") pageNumber: String): Flowable<ProfileResponseModel>

    @POST("logout")
    fun logout(@Header("Authorization") authorization: String, @Query("imei") imei: String, @Query("simSerial") simSerial: String): Flowable<BaseResponseModel>
}