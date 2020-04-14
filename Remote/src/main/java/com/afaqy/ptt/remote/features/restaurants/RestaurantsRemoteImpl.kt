package com.afaqy.ptt.remote.features.restaurants

import com.afaqy.ptt.data.features.restaurants.model.RestaurantEntity
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsRemote
import com.afaqy.ptt.remote.features.restaurants.mapper.RestaurantsResponseModelMapper
import com.afaqy.ptt.remote.features.restaurants.service.RestaurantsService
import io.reactivex.Flowable
import javax.inject.Inject

class RestaurantsRemoteImpl @Inject constructor(
    private val service: RestaurantsService,
    private val mapper: RestaurantsResponseModelMapper
) : RestaurantsRemote {

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        //todo - move parameters
        return service.searchRestaurants(1)
            .map {
                it.items.map { model -> mapper.mapFromModel(model) }
            }
    }

}