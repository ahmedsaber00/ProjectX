package io.android.projectx.remote.features.profile.model

import com.google.gson.annotations.SerializedName

class ProfileModel(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
)