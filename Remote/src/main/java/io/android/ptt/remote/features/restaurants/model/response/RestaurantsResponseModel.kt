package io.android.ptt.remote.features.restaurants.model.response

import com.google.gson.annotations.SerializedName
import io.android.ptt.remote.features.restaurants.model.RestaurantModel

class RestaurantsResponseModel(@SerializedName("content") val items: List<RestaurantModel>)