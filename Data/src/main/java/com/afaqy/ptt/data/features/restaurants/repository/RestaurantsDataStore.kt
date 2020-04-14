package com.afaqy.ptt.data.features.restaurants.repository

import com.afaqy.ptt.data.features.restaurants.model.RestaurantEntity
import io.reactivex.Flowable

interface RestaurantsDataStore {

    fun getRestaurants(): Flowable<List<RestaurantEntity>>
}