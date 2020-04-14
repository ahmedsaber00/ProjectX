package io.android.ptt.data.features.channels.store

import io.android.ptt.data.features.channels.model.ChannelsEntity
import io.android.ptt.data.features.channels.repository.ChannelsDataStore
import io.android.ptt.data.features.channels.repository.ChannelsRemote
import io.reactivex.Flowable
import javax.inject.Inject

open class ChannelsRemoteDataStore @Inject constructor(
    private val channelsRemote: ChannelsRemote
) : ChannelsDataStore {

    override fun getChannels(authorization: String,page: Int): Flowable<List<ChannelsEntity>> {
        return channelsRemote.getChannels(authorization,page)
    }

}