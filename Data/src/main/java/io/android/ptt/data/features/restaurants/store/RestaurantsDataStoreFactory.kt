package io.android.ptt.data.features.restaurants.store

import io.android.ptt.data.features.restaurants.repository.RestaurantsDataStore
import javax.inject.Inject

open class RestaurantsDataStoreFactory @Inject constructor(
    private val restaurantsCacheDateStore: RestaurantsCacheDateStore,
    private val restaurantsRemoteDataStore: RestaurantsRemoteDataStore
) {

    open fun getDataStore(): RestaurantsDataStore {
        return restaurantsRemoteDataStore
    }

}