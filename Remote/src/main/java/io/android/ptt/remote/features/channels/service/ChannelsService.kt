package io.android.ptt.remote.features.channels.service

import io.android.ptt.remote.features.channels.model.response.ChannelsResponseModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ChannelsService {

    @GET("channels")
    fun getChannels(@Header("Authorization") authorization: String, @Query("page") pageNumber: Int): Flowable<ChannelsResponseModel>
}