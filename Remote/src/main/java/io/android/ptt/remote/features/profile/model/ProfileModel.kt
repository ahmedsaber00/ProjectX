package io.android.ptt.remote.features.profile.model

import com.google.gson.annotations.SerializedName

class ProfileModel(
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("countryCode") val countryCode: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("ssn") val ssn: String?,
    @SerializedName("username") val username: String?
)