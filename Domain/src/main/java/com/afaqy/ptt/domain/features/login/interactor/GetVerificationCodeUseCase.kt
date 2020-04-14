package com.afaqy.ptt.domain.features.login.interactor

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.login.repository.ForgetPasswordRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetVerificationCodeUseCase @Inject constructor(
    private val forgetPasswordRepository: ForgetPasswordRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<BaseMessageModel, GetVerificationCodeUseCase.Params?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<BaseMessageModel> {
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