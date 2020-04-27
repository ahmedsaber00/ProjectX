package com.afaqy.ptt.presentation.features.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.afaqy.ptt.domain.features.editprofile.interactor.EditProfileUseCase
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.presentation.base.mapper.BaseViewMapper
import com.afaqy.ptt.presentation.base.model.BaseErrorView
import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import io.reactivex.observers.DisposableObserver
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val mapper: BaseViewMapper
) : ViewModel() {

    private val liveDataEditProfile: MutableLiveData<Resource<BaseMessageView>> = MutableLiveData()

    override fun onCleared() {
        editProfileUseCase?.dispose()
        super.onCleared()
    }

    fun getEditProfile(): LiveData<Resource<BaseMessageView>> {
        return liveDataEditProfile
    }

    fun fetchEditProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody,
        photo: MultipartBody.Part?
    ) {
        liveDataEditProfile.postValue(Resource(ResourceState.LOADING, null, null))
        editProfileUseCase?.execute(
            EditProfileSubscriber(),
            EditProfileUseCase.Params(
                authorization,
                method,
                name,
                email,
                countryCode,
                mobile,
                address,
                photo
            )
        )
    }

    fun fetchEditProfile(
        authorization: String,
        method: RequestBody,
        name: RequestBody,
        email: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        address: RequestBody
    ) {
        liveDataEditProfile.postValue(Resource(ResourceState.LOADING, null, null))
        editProfileUseCase?.execute(
            EditProfileSubscriber(),
            EditProfileUseCase.Params(
                authorization,
                method,
                name,
                email,
                countryCode,
                mobile,
                address
            )
        )
    }

    inner class EditProfileSubscriber : DisposableObserver<BaseMessageModel>() {

        override fun onNext(t: BaseMessageModel) {
            liveDataEditProfile.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    mapper.mapToView(t),
                    null
                )
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            try {
                if (e.localizedMessage.equals("HTTP 422 Unprocessable Entity")) {
                    var baseError: BaseErrorView? = null
                    var stringBuilder: StringBuilder? = StringBuilder("")

                    (e as HttpException).response()?.errorBody()?.let {
                        baseError = Gson().fromJson(it.string(), BaseErrorView::class.java)
                    }
                    for (error: String in baseError?.errors!!) {
                        stringBuilder?.append(error)?.append("\n")
                    }
                    liveDataEditProfile.postValue(
                        Resource(
                            ResourceState.ERROR,
                            null,
                            stringBuilder.toString()
                        )
                    )
                } else {
                    liveDataEditProfile.postValue(
                        Resource(
                            ResourceState.ERROR,
                            null,
                            e.localizedMessage
                        )
                    )
                }
            } catch (exception: Exception) {
                liveDataEditProfile.postValue(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        e.localizedMessage
                    )
                )
            }
        }
    }

}