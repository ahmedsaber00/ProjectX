package io.android.projectx.data.features.channels.store

import io.android.projectx.data.features.restaurants.model.RestaurantEntity
import io.android.projectx.data.features.restaurants.repository.RestaurantsCache
import io.android.projectx.data.features.restaurants.repository.RestaurantsDataStore
import io.reactivex.Flowable
import javax.inject.Inject

open class ChannelsCacheDateStore @Inject constructor(
    private val restaurantsCache: RestaurantsCache
) : RestaurantsDataStore {

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        throw UnsupportedOperationException("Getting Restaurants isn't supported here...")
    }

}