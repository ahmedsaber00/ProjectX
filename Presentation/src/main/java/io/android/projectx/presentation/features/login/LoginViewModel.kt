package io.android.projectx.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.domain.features.login.interactor.Login
import io.android.projectx.domain.features.login.model.LoginModel
import io.android.projectx.presentation.base.mapper.LoginViewMapper
import io.android.projectx.presentation.base.model.LoginView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val login: Login?,
    private val mapper: LoginViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<LoginView>> = MutableLiveData()

    override fun onCleared() {
        login?.dispose()
        super.onCleared()
    }

    fun login(): LiveData<Resource<LoginView>> {
        return liveData
    }

    fun fetchLogin(username: String , password:String , imei:String) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        login?.execute(LoginSubscriber(),Login.Params(username,password,imei))
    }

    inner class LoginSubscriber : DisposableObserver<LoginModel>() {

        override fun onNext(t: LoginModel) {
            liveData.postValue(
                Resource.success(mapper.mapToView(t))
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

}