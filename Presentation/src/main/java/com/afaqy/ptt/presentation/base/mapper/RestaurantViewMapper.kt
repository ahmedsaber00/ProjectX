package com.afaqy.ptt.presentation.base.mapper

import com.afaqy.ptt.domain.features.restaurants.model.Restaurant
import com.afaqy.ptt.presentation.base.model.RestaurantView
import javax.inject.Inject

open class RestaurantViewMapper @Inject constructor() : Mapper<RestaurantView, Restaurant> {

    override fun mapToView(type: Restaurant): RestaurantView {
        return RestaurantView(type.id, type.title, type.description, type.url, type.urlToImage)
    }
}