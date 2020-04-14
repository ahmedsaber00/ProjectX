package io.android.ptt.remote.features.login.model

import com.google.gson.annotations.SerializedName

class BaseMessageModel(
    @SerializedName("message") val message: String
)