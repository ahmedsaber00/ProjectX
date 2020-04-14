package com.afaqy.ptt.remote.features.channels.mapper

import com.afaqy.ptt.data.features.channels.model.ChannelsEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.channels.model.ChannelsModel
import javax.inject.Inject

open class ChannelsResponseModelMapper @Inject constructor() :
    ModelMapper<ChannelsModel, ChannelsEntity> {

    override fun mapFromModel(model: ChannelsModel): ChannelsEntity {
        return ChannelsEntity(
            model.code, model.name
        )
    }

}