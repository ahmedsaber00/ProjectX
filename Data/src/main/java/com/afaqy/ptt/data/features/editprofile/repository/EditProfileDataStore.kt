package com.afaqy.ptt.data.features.editprofile.repository

import com.afaqy.ptt.data.base.model.BaseEntity
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface EditProfileDataStore {

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