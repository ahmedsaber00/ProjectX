package com.afaqy.ptt.presentation.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afaqy.ptt.domain.features.profile.interactor.LogoutUseCase
import com.afaqy.ptt.domain.features.profile.interactor.ProfileUseCase
import com.afaqy.ptt.domain.features.profile.model.ProfileModel
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.presentation.base.mapper.BaseViewMapper
import com.afaqy.ptt.presentation.base.mapper.ProfileViewMapper
import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.presentation.base.model.ProfileView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
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

    fun fetchProfile(authorization: String) {
        liveDataProfile.postValue(Resource(ResourceState.LOADING, null, null))
        getProfileUseCase?.execute(ProfileSubscriber(),ProfileUseCase.Params(authorization))
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