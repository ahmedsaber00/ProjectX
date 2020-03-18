package io.android.projectx.remote.features.channels.mapper

import io.android.projectx.data.features.channels.model.ChannelsEntity
import io.android.projectx.remote.base.mapper.ModelMapper
import io.android.projectx.remote.features.channels.model.ChannelsModel
import javax.inject.Inject

open class ChannelsResponseModelMapper @Inject constructor() :
    ModelMapper<ChannelsModel, ChannelsEntity> {

    override fun mapFromModel(model: ChannelsModel): ChannelsEntity {
        return ChannelsEntity(
            model.code, model.name
        )
    }

}