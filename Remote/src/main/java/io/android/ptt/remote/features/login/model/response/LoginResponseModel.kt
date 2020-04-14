package io.android.ptt.remote.features.login.model.response

import com.google.gson.annotations.SerializedName
import io.android.ptt.remote.base.response.BaseResponseModel
import io.android.ptt.remote.features.login.model.LoginModel

data class LoginResponseModel(@SerializedName("data") val loginModel: LoginModel) : BaseResponseModel()