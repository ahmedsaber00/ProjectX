package io.android.ptt.data.features.channels.repository

import io.android.ptt.data.features.channels.model.ChannelsEntity
import io.reactivex.Flowable

interface ChannelsDataStore {

    fun getChannels(authorization: String,page: Int): Flowable<List<ChannelsEntity>>
}