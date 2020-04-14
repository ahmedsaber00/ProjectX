package io.android.ptt.data.features.restaurants.repository

import io.android.ptt.data.features.restaurants.model.RestaurantEntity
import io.reactivex.Flowable

interface RestaurantsCache {

    fun getRestaurants(): Flowable<List<RestaurantEntity>>
}