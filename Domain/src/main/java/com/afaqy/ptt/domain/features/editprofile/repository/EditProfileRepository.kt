package com.afaqy.ptt.domain.features.editprofile.repository

import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface EditProfileRepository {

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
    ): Observable<BaseMessageModel>

    fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        ssn: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody
    ): Observable<BaseMessageModel>

}