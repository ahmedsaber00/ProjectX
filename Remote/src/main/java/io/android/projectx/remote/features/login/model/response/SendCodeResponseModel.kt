package io.android.projectx.remote.features.login.model.response

import com.google.gson.annotations.SerializedName
import io.android.projectx.remote.base.response.BaseResponseModel
import io.android.projectx.remote.features.login.model.SendCodeModel

data class SendCodeResponseModel(@SerializedName("data") val sendCodeModel: SendCodeModel) : BaseResponseModel()