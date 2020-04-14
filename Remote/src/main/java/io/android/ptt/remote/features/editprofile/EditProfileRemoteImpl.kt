package io.android.ptt.remote.features.editprofile

import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.data.features.editprofile.repository.EditProfileRemote
import io.android.ptt.remote.features.editprofile.service.EditProfileService
import io.android.ptt.remote.features.login.mapper.BaseResponseModelMapper
import io.android.ptt.remote.features.login.model.BaseMessageModel
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class EditProfileRemoteImpl @Inject constructor(
    private val service: EditProfileService,
    private val mapper: BaseResponseModelMapper
) : EditProfileRemote {

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
        return service.editProfile(authorization,method,name, email, ssn, countryCode, mobile, address, photo)
            .map {
                mapper.mapFromModel(BaseMessageModel(it.message))
            }
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
        return service.editProfile(authorization,method,name, email, ssn, countryCode, mobile, address)
            .map {
                mapper.mapFromModel(BaseMessageModel(it.message))
            }
    }

}