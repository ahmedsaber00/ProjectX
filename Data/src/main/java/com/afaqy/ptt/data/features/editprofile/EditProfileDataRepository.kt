package com.afaqy.ptt.data.features.editprofile

import com.afaqy.ptt.data.features.editprofile.store.EditProfileDataStoreFactory
import com.afaqy.ptt.data.features.login.mapper.BaseMessageMapper
import com.afaqy.ptt.data.features.profile.store.ProfileDataStoreFactory
import com.afaqy.ptt.domain.features.editprofile.repository.EditProfileRepository
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.profile.repository.LogoutRepository
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class EditProfileDataRepository @Inject constructor(
    private val mapper: BaseMessageMapper,
    private val factory: EditProfileDataStoreFactory
) :EditProfileRepository {

    override fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody,
        photo: MultipartBody.Part?
    ): Observable<BaseMessageModel> {
        return factory.getDataStore(false, true).editProfile(authorization,method,name, email, countryCode, mobile, address, photo)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }

    override fun editProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody
    ): Observable<BaseMessageModel> {
        return factory.getDataStore(false, true).editProfile(authorization,method,name, email, countryCode, mobile, address)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }

    override fun editProfile(
        authorization: String,
        method: RequestBody,
        currentPassword: RequestBody,
        password: RequestBody,
        passwordConfirmation: RequestBody): Observable<BaseMessageModel> {
        return factory.getDataStore(false, true).editProfile(authorization,method,currentPassword,password, passwordConfirmation)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }
}