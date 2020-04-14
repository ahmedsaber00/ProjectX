package io.android.ptt.remote.features.profile

import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.data.features.profile.model.ProfileEntity
import io.android.ptt.data.features.profile.repository.ProfileRemote
import io.android.ptt.remote.features.login.mapper.BaseResponseModelMapper
import io.android.ptt.remote.features.login.model.BaseMessageModel
import io.android.ptt.remote.features.profile.mapper.ProfileResponseModelMapper
import io.android.ptt.remote.features.profile.service.ProfileService
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

    override fun getProfile(authorization: String): Flowable<ProfileEntity> {
        return service.getProfile(authorization)
            .map {
                mapper.mapFromModel(it.profileModel)
            }
    }
}