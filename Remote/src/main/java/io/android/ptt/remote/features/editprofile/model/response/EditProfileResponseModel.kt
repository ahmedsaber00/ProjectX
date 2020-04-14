package io.android.ptt.remote.features.editprofile.model.response

import com.google.gson.annotations.SerializedName
import io.android.ptt.remote.features.profile.model.ProfileModel

class EditProfileResponseModel(@SerializedName("data") val profileModel: ProfileModel)