package io.android.projectx.data.features.channels.mapper

import io.android.projectx.data.base.mapper.EntityMapper
import io.android.projectx.data.features.channels.model.ChannelsEntity
import io.android.projectx.domain.features.channels.model.ChannelModel
import javax.inject.Inject

open class ChannelsMapper @Inject constructor() :
    EntityMapper<ChannelsEntity, ChannelModel> {

    override fun mapFromEntity(entity: ChannelsEntity): ChannelModel {
        return ChannelModel(
            entity.code, entity.name)
    }

    override fun mapToEntity(domain: ChannelModel): ChannelsEntity {
        return ChannelsEntity(
            domain.code, domain.name)
    }

}