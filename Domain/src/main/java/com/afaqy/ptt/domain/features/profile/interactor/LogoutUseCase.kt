package com.afaqy.ptt.domain.features.profile.interactor

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.profile.repository.LogoutRepository
import io.reactivex.Observable
import javax.inject.Inject

open class LogoutUseCase @Inject constructor(
    private val logoutRepository: LogoutRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<BaseMessageModel, LogoutUseCase.Params?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<BaseMessageModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return logoutRepository.logout(params.authorization, params.imei, params.simSerial)
    }

    data class Params constructor(
        val authorization: String,
        val imei: String,
        val simSerial: String
    ) {
        companion object {
            fun forLogout(authorization: String, imei: String, simSerial: String): Params {
                return Params(authorization, imei, simSerial)
            }
        }
    }

}