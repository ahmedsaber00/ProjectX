package com.afaqy.ptt.remote.features.channels.model.response

import com.google.gson.annotations.SerializedName
import com.afaqy.ptt.remote.features.channels.model.ChannelsModel

class ChannelsResponseModel(@SerializedName("data") val items: List<ChannelsModel>)