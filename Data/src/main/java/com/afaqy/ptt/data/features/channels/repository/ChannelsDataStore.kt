package com.afaqy.ptt.data.features.channels.repository

import com.afaqy.ptt.data.features.channels.model.ChannelsEntity
import io.reactivex.Flowable

interface ChannelsDataStore {

    fun getChannels(authorization: String,page: Int): Flowable<List<ChannelsEntity>>
}