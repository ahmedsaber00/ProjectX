package io.android.ptt.remote.features.restaurants.model

import com.google.gson.annotations.SerializedName

class RestaurantModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("method_description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("url_to_image") val urlToImage: String
)