package io.android.ptt.remote.features.login.model.response

import com.google.gson.annotations.SerializedName
import io.android.ptt.remote.base.response.BaseResponseModel
import io.android.ptt.remote.features.login.model.SendCodeModel

data class SendCodeResponseModel(@SerializedName("data") val sendCodeModel: SendCodeModel) : BaseResponseModel()