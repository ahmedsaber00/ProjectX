package io.android.projectx.presentation.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.android.projectx.domain.features.profile.interactor.LogoutUseCase
import io.android.projectx.domain.features.profile.interactor.ProfileUseCase
import io.android.projectx.domain.features.profile.model.ProfileModel
import io.android.projectx.domain.features.login.model.BaseMessageModel
import io.android.projectx.presentation.base.mapper.BaseViewMapper
import io.android.projectx.presentation.base.mapper.ProfileViewMapper
import io.android.projectx.presentation.base.model.BaseMessageView
import io.android.projectx.presentation.base.model.ProfileView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: ProfileUseCase?,
    private val mapper: ProfileViewMapper,
    private val logoutUseCase: LogoutUseCase?,
    private val baseViewMapper: BaseViewMapper
) : ViewModel() {

    private val liveDataProfile: MutableLiveData<Resource<ProfileView>> = MutableLiveData()
    private val liveDataLogout: MutableLiveData<Resource<BaseMessageView>> = MutableLiveData()

    override fun onCleared() {
        getProfileUseCase?.dispose()
        super.onCleared()
    }

    fun getProfile(): LiveData<Resource<ProfileView>> {
        return liveDataProfile
    }

    fun fetchProfile(authorization: String,page:String) {
        liveDataProfile.postValue(Resource(ResourceState.LOADING, null, null))
        getProfileUseCase?.execute(ProfileSubscriber(),ProfileUseCase.Params(authorization,page))
    }

    fun getLogout(): LiveData<Resource<BaseMessageView>> {
        return liveDataLogout
    }

    fun fetchLogout(authorization: String, imei: String, simSerial: String) {
        liveDataLogout.postValue(Resource(ResourceState.LOADING, null, null))
        logoutUseCase?.execute(LogoutSubscriber(),LogoutUseCase.Params(authorization,imei,simSerial))
    }

    inner class ProfileSubscriber : DisposableObserver<ProfileModel>() {

        override fun onNext(t: ProfileModel) {
            liveDataProfile.postValue(
                Resource(
                    ResourceState.SUCCESS,
                     mapper.mapToView(t),
                    null
                )
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveDataProfile.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    inner class LogoutSubscriber : DisposableObserver<BaseMessageModel>() {

        override fun onNext(t: BaseMessageModel) {
            liveDataLogout.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    baseViewMapper.mapToView(t),
                    null
                )
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveDataLogout.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

}