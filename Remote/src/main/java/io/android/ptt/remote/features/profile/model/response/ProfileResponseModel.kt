package io.android.ptt.remote.features.profile.model.response

import com.google.gson.annotations.SerializedName
import io.android.ptt.remote.features.profile.model.ProfileModel

class ProfileResponseModel(@SerializedName("data") val profileModel: ProfileModel)