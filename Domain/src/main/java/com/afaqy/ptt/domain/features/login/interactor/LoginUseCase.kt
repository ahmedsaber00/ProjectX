package com.afaqy.ptt.domain.features.login.interactor

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.login.model.LoginModel
import com.afaqy.ptt.domain.features.login.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

open class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<LoginModel, LoginUseCase.Params?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<LoginModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return loginRepository.login(
            params.username,
            params.password,
            params.imei,
            params.simSerial
        )
    }

    data class Params constructor(
        val username: String,
        val password: String,
        val imei: String,
        val simSerial: String
    ) {
        companion object {
            fun forLogin(
                username: String,
                password: String,
                imei: String,
                simSerial: String
            ): Params {
                return Params(
                    username,
                    password,
                    imei,
                    simSerial
                )
            }
        }
    }

}