package com.afaqy.ptt.domain.features.editpassword.interactor

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.editprofile.repository.EditProfileRepository
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import io.reactivex.Observable
import okhttp3.RequestBody
import javax.inject.Inject

open class EditPasswordUseCase @Inject constructor(
    private val editPasswordRepository: EditProfileRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<BaseMessageModel, EditPasswordUseCase.Params?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<BaseMessageModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return editPasswordRepository.editProfile(params.authorization, params.method, params.currentPassword,
            params.password,params.passwordConfirmation)
    }

    data class Params constructor(
        val authorization: String,
        val method: RequestBody,
        val currentPassword: RequestBody,
        val password: RequestBody,
        val passwordConfirmation: RequestBody) {
        companion object {
            fun forEditProfile(
                authorization: String,
                method: RequestBody,
                currentPassword: RequestBody,
                password: RequestBody,
                passwordConfirmation: RequestBody): Params {
                return Params(authorization, method, currentPassword, password, passwordConfirmation)
            }
        }
    }

}