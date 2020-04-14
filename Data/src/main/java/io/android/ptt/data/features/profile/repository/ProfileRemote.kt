package io.android.ptt.data.features.profile.repository

import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.data.features.profile.model.ProfileEntity
import io.reactivex.Flowable

interface ProfileRemote {

    fun logout(authorization: String , imei:String , simSerial:String): Flowable<BaseEntity>

    fun getProfile(authorization: String): Flowable<ProfileEntity>

}