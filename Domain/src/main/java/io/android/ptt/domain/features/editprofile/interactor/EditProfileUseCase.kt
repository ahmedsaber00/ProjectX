package io.android.ptt.domain.features.editprofile.interactor

import io.android.ptt.domain.base.executor.PostExecutionThread
import io.android.ptt.domain.base.interactor.ObservableUseCase
import io.android.ptt.domain.features.editprofile.repository.EditProfileRepository
import io.android.ptt.domain.features.login.model.BaseMessageModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

open class EditProfileUseCase @Inject constructor(
    private val editProfileRepository: EditProfileRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<BaseMessageModel, EditProfileUseCase.Params?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<BaseMessageModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        if (params.photo!=null)
        return editProfileRepository.editProfile(params.authorization, params.method,params.name,params.email
            ,params.ssn,params.countryCode,params.mobile,params.address,params.photo)
        else
            return editProfileRepository.editProfile(params.authorization, params.method,params.name,params.email
                ,params.ssn,params.countryCode,params.mobile,params.address)

    }

    data class Params constructor(
        val authorization: String,
        val method: RequestBody,
        val name: RequestBody,
        val email: RequestBody,
        val ssn: RequestBody,
        val countryCode: RequestBody,
        val mobile: RequestBody,
        val address: RequestBody,
        val photo: MultipartBody.Part? = null) {
        companion object {
            fun forEditProfile(
                authorization: String,
                method: RequestBody,
                name: RequestBody,
                email: RequestBody,
                ssn: RequestBody,
                countryCode: RequestBody,
                mobile: RequestBody,
                address: RequestBody,
                photo: MultipartBody.Part): Params {
                return Params(authorization, method, name, email, ssn, countryCode, mobile, address, photo)
            }
            fun forEditProfile(
                authorization: String,
                method: RequestBody,
                name: RequestBody,
                email: RequestBody,
                ssn: RequestBody,
                countryCode: RequestBody,
                mobile: RequestBody,
                address: RequestBody): Params {
                return Params(authorization, method, name, email, ssn, countryCode, mobile, address)
            }
        }
    }

}