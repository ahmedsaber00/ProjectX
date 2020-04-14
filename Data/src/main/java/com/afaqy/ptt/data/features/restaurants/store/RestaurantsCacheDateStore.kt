package com.afaqy.ptt.data.features.restaurants.store

import com.afaqy.ptt.data.features.restaurants.model.RestaurantEntity
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsCache
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsDataStore
import io.reactivex.Flowable
import javax.inject.Inject

open class RestaurantsCacheDateStore @Inject constructor(
    private val restaurantsCache: RestaurantsCache
) : RestaurantsDataStore {

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        throw UnsupportedOperationException("Getting Restaurants isn't supported here...")
    }

}