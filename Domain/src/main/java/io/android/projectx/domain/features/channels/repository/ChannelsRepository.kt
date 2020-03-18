package io.android.projectx.domain.features.channels.repository

import io.android.projectx.domain.features.channels.model.ChannelModel
import io.reactivex.Observable

interface ChannelsRepository {

    fun getChannels(authorization: String,page:Int): Observable<List<ChannelModel>>

}