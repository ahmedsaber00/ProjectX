package io.android.projectx.remote.features.channels

import io.android.projectx.data.features.channels.model.ChannelsEntity
import io.android.projectx.data.features.channels.repository.ChannelsRemote
import io.android.projectx.remote.features.channels.mapper.ChannelsResponseModelMapper
import io.android.projectx.remote.features.channels.service.ChannelsService
import io.reactivex.Flowable
import javax.inject.Inject

class ChannelsRemoteImpl @Inject constructor(
    private val service: ChannelsService,
    private val mapper: ChannelsResponseModelMapper
) : ChannelsRemote {

    override fun getChannels(authorization: String,page: Int): Flowable<List<ChannelsEntity>> {
        //todo - move parameters
        return service.getChannels(authorization,page)
            .map {
                it.items.map { model -> mapper.mapFromModel(model) }
            }
    }

}