package io.android.ptt.presentation.base.mapper

import io.android.ptt.domain.features.channels.model.ChannelModel
import io.android.ptt.presentation.base.model.ChannelsView
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