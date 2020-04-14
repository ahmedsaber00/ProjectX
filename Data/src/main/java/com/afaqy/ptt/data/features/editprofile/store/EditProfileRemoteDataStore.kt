package com.afaqy.ptt.data.features.editprofile.store

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.data.features.editprofile.repository.EditProfileDataStore
import com.afaqy.ptt.data.features.editprofile.repository.EditProfileRemote
import com.afaqy.ptt.data.features.profile.model.ProfileEntity
import com.afaqy.ptt.data.features.profile.repository.ProfileDataStore
import com.afaqy.ptt.data.features.profile.repository.ProfileRemote
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

open class EditProfileRemoteDataStore @Inject constructor(
    private val profileRemote: EditProfileRemote
) : EditProfileDataStore {
    override fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        ssn: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody,
        photo: MultipartBody.Part?
    ): Flowable<BaseEntity> {
        return profileRemote.editProfile(authorization,method,name, email, ssn, countryCode, mobile, address, photo)
    }

    override fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        ssn: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody
    ): Flowable<BaseEntity> {
        return profileRemote.editProfile(authorization,method,name, email, ssn, countryCode, mobile, address)
    }

}