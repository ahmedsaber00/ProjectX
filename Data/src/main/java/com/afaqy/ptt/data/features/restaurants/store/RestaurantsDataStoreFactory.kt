package com.afaqy.ptt.data.features.restaurants.store

import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsDataStore
import javax.inject.Inject

open class RestaurantsDataStoreFactory @Inject constructor(
    private val restaurantsCacheDateStore: RestaurantsCacheDateStore,
    private val restaurantsRemoteDataStore: RestaurantsRemoteDataStore
) {

    open fun getDataStore(): RestaurantsDataStore {
        return restaurantsRemoteDataStore
    }

}