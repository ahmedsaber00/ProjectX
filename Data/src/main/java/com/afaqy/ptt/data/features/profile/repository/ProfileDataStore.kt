package com.afaqy.ptt.data.features.profile.repository

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.data.features.profile.model.ProfileEntity
import io.reactivex.Flowable

interface ProfileDataStore {

    fun getProfile(authorization: String): Flowable<ProfileEntity>

    fun logout(
        authorization: String,
        imei: String,
        simSerial: String
    ): Flowable<BaseEntity>

}