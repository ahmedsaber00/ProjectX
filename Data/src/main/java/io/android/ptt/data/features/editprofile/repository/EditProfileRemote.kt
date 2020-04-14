package io.android.ptt.data.features.editprofile.repository

import io.android.ptt.data.base.model.BaseEntity
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface EditProfileRemote {

    fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        ssn: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody,
        photo: MultipartBody.Part?
    ): Flowable<BaseEntity>

    fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        ssn: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody
    ): Flowable<BaseEntity>

}