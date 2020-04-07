package io.android.projectx.data.features.profile.repository

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.profile.model.ProfileEntity
import io.reactivex.Flowable

interface ProfileDataStore {

    fun getProfile(authorization: String, simSerial: String): Flowable<ProfileEntity>

    fun logout(
        authorization: String,
        imei: String,
        simSerial: String
    ): Flowable<BaseEntity>

}