package com.afaqy.ptt.data.features.channels.store

import com.afaqy.ptt.data.features.channels.repository.ChannelsDataStore
import javax.inject.Inject

open class ChannelsDataStoreFactory @Inject constructor(
    private val channelsCacheDateStore: ChannelsCacheDateStore,
    private val channelsRemoteDataStore: ChannelsRemoteDataStore
) {

    open fun getDataStore(): ChannelsDataStore {
        return channelsRemoteDataStore
    }

}