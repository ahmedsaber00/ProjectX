package com.afaqy.ptt.data.features.channels.mapper

import com.afaqy.ptt.data.base.mapper.EntityMapper
import com.afaqy.ptt.data.features.channels.model.ChannelsEntity
import com.afaqy.ptt.domain.features.channels.model.ChannelModel
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