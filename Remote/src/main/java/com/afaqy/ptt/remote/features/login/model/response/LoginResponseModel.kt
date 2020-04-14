package com.afaqy.ptt.remote.features.login.model.response

import com.google.gson.annotations.SerializedName
import com.afaqy.ptt.remote.base.response.BaseResponseModel
import com.afaqy.ptt.remote.features.login.model.LoginModel

data class LoginResponseModel(@SerializedName("data") val loginModel: LoginModel) : BaseResponseModel()