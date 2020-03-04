package io.android.projectx.remote.features.login.model

import com.google.gson.annotations.SerializedName
import java.util.*

class LoginModel(
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("expiresIn") val expiresIn: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)