package com.afaqy.ptt.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afaqy.ptt.domain.features.login.interactor.GetVerificationCodeUseCase
import com.afaqy.ptt.domain.features.login.interactor.LoginUseCase
import com.afaqy.ptt.domain.features.login.interactor.ResetPasswordUseCase
import com.afaqy.ptt.domain.features.login.interactor.SendVerificationCodeUseCase
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.login.model.LoginModel
import com.afaqy.ptt.domain.features.login.model.SendCodeModel
import com.afaqy.ptt.presentation.base.mapper.BaseViewMapper
import com.afaqy.ptt.presentation.base.mapper.LoginViewMapper
import com.afaqy.ptt.presentation.base.mapper.SendVerificationViewMapper
import com.afaqy.ptt.presentation.base.model.BaseErrorView
import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.presentation.base.model.LoginView
import com.afaqy.ptt.presentation.base.model.SendCodeView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import com.google.gson.Gson
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase?,
    private val getVerificationCodeUseCase: GetVerificationCodeUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val loginMapper: LoginViewMapper,
    private val sendVerificationViewMapper: SendVerificationViewMapper,
    private val baseMapper: BaseViewMapper
) : ViewModel() {

    private val liveDataLogin: MutableLiveData<Resource<LoginView>> = MutableLiveData()
    private val liveDataGetVerificationCode: MutableLiveData<Resource<BaseMessageView>> =
        MutableLiveData()
    private val liveDataResetPassword: MutableLiveData<Resource<BaseMessageView>> =
        MutableLiveData()
    private val liveDataSendVerification: MutableLiveData<Resource<SendCodeView>> =
        MutableLiveData()

    override fun onCleared() {
        loginUseCase?.dispose()
        super.onCleared()
    }

    fun login(): LiveData<Resource<LoginView>> {
        return liveDataLogin
    }

    fun getVerificationCode(): LiveData<Resource<BaseMessageView>> {
        return liveDataGetVerificationCode
    }

    fun resetPassword(): LiveData<Resource<BaseMessageView>> {
        return liveDataResetPassword
    }

    fun sendVerification(): LiveData<Resource<SendCodeView>> {
        return liveDataSendVerification
    }

    fun fetchLogin(username: String, password: String, imei: String, simSerial: String) {
        liveDataLogin.postValue(Resource(ResourceState.LOADING, null, null))
        loginUseCase?.execute(
            LoginSubscriber(),
            LoginUseCase.Params(username, password, imei, simSerial)
        )
    }

    fun fetchVerificationCode(simSerial: String) {
        liveDataGetVerificationCode.postValue(Resource(ResourceState.LOADING, null, null))
        getVerificationCodeUseCase?.execute(
            GetVerificationSubscriber(),
            GetVerificationCodeUseCase.Params(simSerial)
        )
    }

    fun fetchSendVerificationCode(simSerial: String, code: String) {
        liveDataResetPassword.postValue(Resource(ResourceState.LOADING, null, null))
        sendVerificationCodeUseCase?.execute(
            SendVerificationSubscriber(),
            SendVerificationCodeUseCase.Params(simSerial, code)
        )
    }

    fun fetchResetPassword(
        token: String,
        simSerial: String,
        password: String,
        passwordConfirmation: String
    ) {
        liveDataResetPassword.postValue(Resource(ResourceState.LOADING, null, null))
        resetPasswordUseCase?.execute(
            ResetPasswordSubscriber(),
            ResetPasswordUseCase.Params(token, simSerial, password, passwordConfirmation)
        )
    }

    inner class LoginSubscriber : DisposableObserver<LoginModel>() {

        override fun onNext(t: LoginModel) {
            liveDataLogin.postValue(
                Resource.success(loginMapper.mapToView(t))
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            if (e.localizedMessage.equals("422 Unprocessable Entity")) {
                var baseError: BaseErrorView? = null
                var stringBuilder: StringBuilder? = StringBuilder("")

                (e as HttpException).response()?.errorBody()?.let {
                    baseError = Gson().fromJson(it.string(), BaseErrorView::class.java)
                }
                for (error: String in baseError?.errors!!) {
                    stringBuilder?.append(error)?.append("\n")
                }
                liveDataLogin.postValue(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        stringBuilder.toString()
                    )
                )
            }else if (e.localizedMessage.equals("400 Bad Request")) {
                var baseError: BaseErrorView? = null
                (e as HttpException).response()?.errorBody()?.let {
                    baseError = Gson().fromJson(it.string(), BaseErrorView::class.java)
                }
                liveDataLogin.postValue(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        baseError?.message
                    )
                )
            }else
                liveDataLogin.postValue(Resource.error(e.localizedMessage))
        }

    }

    inner class GetVerificationSubscriber : DisposableObserver<BaseMessageModel>() {

        override fun onNext(t: BaseMessageModel) {
            liveDataGetVerificationCode.postValue(
                Resource.success(baseMapper.mapToView(t))
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveDataGetVerificationCode.postValue(Resource.error(e.localizedMessage))
        }

    }

    inner class ResetPasswordSubscriber : DisposableObserver<BaseMessageModel>() {

        override fun onNext(t: BaseMessageModel) {
            liveDataResetPassword.postValue(
                Resource.success(baseMapper.mapToView(t))
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveDataResetPassword.postValue(Resource.error(e.localizedMessage))
        }

    }

    inner class SendVerificationSubscriber : DisposableObserver<SendCodeModel>() {

        override fun onNext(t: SendCodeModel) {
            liveDataSendVerification.postValue(
                Resource.success(sendVerificationViewMapper.mapToView(t))
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveDataSendVerification.postValue(Resource.error(e.localizedMessage))
        }

    }

}