package io.android.projectx.remote.features.login.model

import com.google.gson.annotations.SerializedName

class SendCodeModel(
    @SerializedName("email") val email: String,
    @SerializedName("token") val token: String
)