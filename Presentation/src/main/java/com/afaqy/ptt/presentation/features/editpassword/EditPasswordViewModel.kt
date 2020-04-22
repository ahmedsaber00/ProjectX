package com.afaqy.ptt.presentation.features.editpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afaqy.ptt.domain.features.editpassword.interactor.EditPasswordUseCase
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

class EditPasswordViewModel @Inject constructor(
    private val editPasswordUseCase: EditPasswordUseCase,
    private val mapper: BaseViewMapper
) : ViewModel() {

    private val liveDataEditPassword: MutableLiveData<Resource<BaseMessageView>> = MutableLiveData()

    override fun onCleared() {
        editPasswordUseCase?.dispose()
        super.onCleared()
    }

    fun getEditProfile(): LiveData<Resource<BaseMessageView>> {
        return liveDataEditPassword
    }

    fun fetchEditProfile(
        authorization: String,
        method: RequestBody,
        currentPassword: RequestBody,
        password: RequestBody,
        passwordConfirmation: RequestBody
    ) {
        liveDataEditPassword.postValue(Resource(ResourceState.LOADING, null, null))
        editPasswordUseCase?.execute(
            EditPasswordSubscriber(),
            EditPasswordUseCase.Params(
                authorization,
                method,
                currentPassword,
                password,
                passwordConfirmation
            )
        )
    }

    inner class EditPasswordSubscriber : DisposableObserver<BaseMessageModel>() {

        override fun onNext(t: BaseMessageModel) {
            liveDataEditPassword.postValue(
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
                    liveDataEditPassword.postValue(
                        Resource(
                            ResourceState.ERROR,
                            null,
                            stringBuilder.toString()
                        )
                    )
                } else {
                    liveDataEditPassword.postValue(
                        Resource(
                            ResourceState.ERROR,
                            null,
                            e.localizedMessage
                        )
                    )
                }
            } catch (exception: Exception) {
                liveDataEditPassword.postValue(
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