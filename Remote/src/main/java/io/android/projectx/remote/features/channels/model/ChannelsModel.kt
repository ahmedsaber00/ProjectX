package io.android.projectx.remote.features.channels.model

import com.google.gson.annotations.SerializedName

class ChannelsModel(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
)