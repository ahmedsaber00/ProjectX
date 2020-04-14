package com.afaqy.ptt.data.features.profile.store

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.data.features.profile.model.ProfileEntity
import com.afaqy.ptt.data.features.profile.repository.ProfileDataStore
import com.afaqy.ptt.data.features.profile.repository.ProfileRemote
import io.reactivex.Flowable
import javax.inject.Inject

open class ProfileRemoteDataStore @Inject constructor(
    private val profileRemote: ProfileRemote
) : ProfileDataStore {

    override fun getProfile(authorization: String): Flowable<ProfileEntity> {
        return profileRemote.getProfile(authorization)
    }

    override fun logout(
        authorization: String,
        imei: String,
        simSerial: String
    ): Flowable<BaseEntity> {
        return profileRemote.logout(authorization,imei,simSerial)
    }

}