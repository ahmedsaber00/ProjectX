package com.afaqy.ptt.data.features.restaurants.store

import com.afaqy.ptt.data.features.restaurants.model.RestaurantEntity
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsDataStore
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsRemote
import io.reactivex.Flowable
import javax.inject.Inject

open class RestaurantsRemoteDataStore @Inject constructor(
    private val restaurantsRemote: RestaurantsRemote
) : RestaurantsDataStore {

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        return restaurantsRemote.getRestaurants()
    }

}