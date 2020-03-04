package io.android.projectx.domain.features.login.interactor

import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.domain.base.interactor.ObservableUseCase
import io.android.projectx.domain.features.login.model.LoginModel
import io.android.projectx.domain.features.login.repository.LoginRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class Login @Inject constructor(
    private val loginRepository: LoginRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<LoginModel, Login.Params?>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Observable<LoginModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return loginRepository.login(params.username,params.password,params.imei)
    }

    data class Params constructor(val username: String , val password:String , val imei:String) {
        companion object {
            fun forLogin(username: String , password:String , imei:String): Params {
                return Params(
                    username,
                    password,
                    imei
                )
            }
        }
    }

}