package io.android.projectx.domain.features.login.interactor

import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.domain.base.interactor.ObservableUseCase
import io.android.projectx.domain.features.login.model.BaseMessageModel
import io.android.projectx.domain.features.login.repository.ForgetPasswordRepository
import io.reactivex.Observable
import javax.inject.Inject

open class ResetPasswordUseCase @Inject constructor(
    private val forgetPasswordRepository: ForgetPasswordRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<BaseMessageModel, ResetPasswordUseCase.Params?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<BaseMessageModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return forgetPasswordRepository.getVerificationCode(params.simSerial)
    }

    data class Params constructor(
        val token: String,
        val simSerial: String,
        val password: String,
        val passwordConfirmation: String
    ) {
        companion object {
            fun forResiting(
                token: String,
                simSerial: String,
                password: String,
                passwordConfirmation: String
            ): Params {
                return Params(
                    token,
                    simSerial,
                    password,
                    passwordConfirmation
                )
            }
        }
    }

}