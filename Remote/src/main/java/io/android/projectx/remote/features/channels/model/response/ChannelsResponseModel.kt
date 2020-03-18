package io.android.projectx.remote.features.channels.model.response

import com.google.gson.annotations.SerializedName
import io.android.projectx.remote.features.channels.model.ChannelsModel

class ChannelsResponseModel(@SerializedName("data") val items: List<ChannelsModel>)