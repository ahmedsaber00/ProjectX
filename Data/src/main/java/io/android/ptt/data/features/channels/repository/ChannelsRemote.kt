package io.android.ptt.data.features.channels.repository

import io.android.ptt.data.features.channels.model.ChannelsEntity
import io.reactivex.Flowable

interface ChannelsRemote {

    fun getChannels(authorization: String,page: Int): Flowable<List<ChannelsEntity>>

}