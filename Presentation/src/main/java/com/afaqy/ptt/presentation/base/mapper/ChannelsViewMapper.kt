package com.afaqy.ptt.presentation.base.mapper

import com.afaqy.ptt.domain.features.channels.model.ChannelModel
import com.afaqy.ptt.presentation.base.model.ChannelsView
import javax.inject.Inject

open class ChannelsViewMapper @Inject constructor() : Mapper<ChannelsView, ChannelModel> {

    override fun mapToView(type: ChannelModel): ChannelsView {
        return ChannelsView(
            type.code,
            type.name,
            false
        )
    }
}