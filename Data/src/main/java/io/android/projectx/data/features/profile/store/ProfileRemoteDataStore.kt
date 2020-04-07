package io.android.projectx.data.features.profile.store

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.profile.model.ProfileEntity
import io.android.projectx.data.features.profile.repository.ProfileDataStore
import io.android.projectx.data.features.profile.repository.ProfileRemote
import io.reactivex.Flowable
import javax.inject.Inject

open class ProfileRemoteDataStore @Inject constructor(
    private val profileRemote: ProfileRemote
) : ProfileDataStore {

    override fun getProfile(authorization: String,simSerial: String): Flowable<ProfileEntity> {
        return profileRemote.getProfile(authorization,simSerial)
    }

    override fun logout(
        authorization: String,
        imei: String,
        simSerial: String
    ): Flowable<BaseEntity> {
        return profileRemote.logout(authorization,imei,simSerial)
    }

}