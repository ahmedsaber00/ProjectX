package io.android.projectx.remote.features.profile

import com.sun.jndi.cosnaming.ExceptionMapper
import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.channels.model.ChannelsEntity
import io.android.projectx.data.features.channels.repository.ChannelsRemote
import io.android.projectx.data.features.profile.model.ProfileEntity
import io.android.projectx.data.features.profile.repository.ProfileRemote
import io.android.projectx.remote.features.channels.mapper.ChannelsResponseModelMapper
import io.android.projectx.remote.features.channels.service.ChannelsService
import io.android.projectx.remote.features.login.mapper.BaseResponseModelMapper
import io.android.projectx.remote.features.login.model.BaseMessageModel
import io.android.projectx.remote.features.profile.mapper.ProfileResponseModelMapper
import io.android.projectx.remote.features.profile.service.ProfileService
import io.reactivex.Flowable
import javax.inject.Inject

class ProfileRemoteImpl @Inject constructor(
    private val service: ProfileService,
    private val mapper: ProfileResponseModelMapper,
    private val baseMapper: BaseResponseModelMapper
) : ProfileRemote {

    override fun logout(
        authorization: String,
        imei: String,
        simSerial: String
    ): Flowable<BaseEntity> {
        return service.logout(authorization,imei,simSerial)
            .map {
                baseMapper.mapFromModel(BaseMessageModel(it.message))
            }
    }

    override fun getProfile(authorization: String,simSerial: String): Flowable<ProfileEntity> {
        return service.getProfile(authorization,simSerial)
            .map {
                mapper.mapFromModel(it.profileModel)
            }
    }
}