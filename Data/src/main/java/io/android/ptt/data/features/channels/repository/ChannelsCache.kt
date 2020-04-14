package io.android.ptt.data.features.channels.repository

import io.android.ptt.data.features.channels.model.ChannelsEntity
import io.reactivex.Flowable

interface ChannelsCache {

    fun getChannels(): Flowable<List<ChannelsEntity>>
}