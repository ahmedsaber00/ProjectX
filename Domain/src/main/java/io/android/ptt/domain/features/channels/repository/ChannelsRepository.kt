package io.android.ptt.domain.features.channels.repository

import io.android.ptt.domain.features.channels.model.ChannelModel
import io.reactivex.Observable

interface ChannelsRepository {

    fun getChannels(authorization: String,page:Int): Observable<List<ChannelModel>>

}