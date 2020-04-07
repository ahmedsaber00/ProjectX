package io.android.projectx.remote.features.login.model

import com.google.gson.annotations.SerializedName

class BaseMessageModel(
    @SerializedName("message") val message: String
)