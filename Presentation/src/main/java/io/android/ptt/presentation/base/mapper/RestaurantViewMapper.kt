package io.android.ptt.presentation.base.mapper

import io.android.ptt.domain.features.restaurants.model.Restaurant
import io.android.ptt.presentation.base.model.RestaurantView
import javax.inject.Inject

open class RestaurantViewMapper @Inject constructor() : Mapper<RestaurantView, Restaurant> {

    override fun mapToView(type: Restaurant): RestaurantView {
        return RestaurantView(type.id, type.title, type.description, type.url, type.urlToImage)
    }
}