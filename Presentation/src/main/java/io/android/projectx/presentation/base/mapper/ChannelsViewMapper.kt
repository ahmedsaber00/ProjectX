package io.android.projectx.presentation.base.mapper

import io.android.projectx.domain.features.channels.model.ChannelModel
import io.android.projectx.presentation.base.model.ChannelsView
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