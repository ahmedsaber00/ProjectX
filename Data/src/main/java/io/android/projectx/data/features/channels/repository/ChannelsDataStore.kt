package io.android.projectx.data.features.channels.repository

import io.android.projectx.data.features.channels.model.ChannelsEntity
import io.reactivex.Flowable

interface ChannelsDataStore {

    fun getChannels(authorization: String,page: Int): Flowable<List<ChannelsEntity>>
}