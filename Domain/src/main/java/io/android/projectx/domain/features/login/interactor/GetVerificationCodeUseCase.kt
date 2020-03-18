package io.android.projectx.domain.features.login.interactor

import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.domain.base.interactor.ObservableUseCase
import io.android.projectx.domain.features.login.model.ForgetPasswordModel
import io.android.projectx.domain.features.login.repository.ForgetPasswordRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetVerificationCodeUseCase @Inject constructor(
    private val forgetPasswordRepository: ForgetPasswordRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<ForgetPasswordModel, GetVerificationCodeUseCase.Params?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<ForgetPasswordModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return forgetPasswordRepository.getVerificationCode(params.simSerial)
    }

    data class Params constructor(val simSerial: String) {
        companion object {
            fun forVerification(simSerial: String): Params {
                return Params(
                    simSerial
                )
            }
        }
    }

}