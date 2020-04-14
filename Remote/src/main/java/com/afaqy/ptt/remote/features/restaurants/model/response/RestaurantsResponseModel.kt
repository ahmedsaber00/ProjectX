package com.afaqy.ptt.remote.features.restaurants.model.response

import com.google.gson.annotations.SerializedName
import com.afaqy.ptt.remote.features.restaurants.model.RestaurantModel

class RestaurantsResponseModel(@SerializedName("content") val items: List<RestaurantModel>)