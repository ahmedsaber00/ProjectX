package com.afaqy.ptt.data.features.channels.repository

import com.afaqy.ptt.data.features.channels.model.ChannelsEntity
import io.reactivex.Flowable

interface ChannelsCache {

    fun getChannels(): Flowable<List<ChannelsEntity>>
}