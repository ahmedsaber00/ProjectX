package io.android.projectx.domain.features.login.interactor

import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.domain.base.interactor.ObservableUseCase
import io.android.projectx.domain.features.login.model.SendCodeModel
import io.android.projectx.domain.features.login.repository.ForgetPasswordRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class SendVerificationCodeUseCase @Inject constructor(
    private val forgetPasswordRepository: ForgetPasswordRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<SendCodeModel, SendVerificationCodeUseCase.Params?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<SendCodeModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return forgetPasswordRepository.sendVerification(params.simSerial,params.code)
    }

    data class Params constructor(val code:String,val simSerial:String) {
        companion object {
            fun forVerification( simSerial:String,code:String): Params {
                return Params(
                    code,
                    simSerial
                )
            }
        }
    }

}