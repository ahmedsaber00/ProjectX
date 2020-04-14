package io.android.ptt.remote.features.restaurants

import io.android.ptt.data.features.restaurants.model.RestaurantEntity
import io.android.ptt.data.features.restaurants.repository.RestaurantsRemote
import io.android.ptt.remote.features.restaurants.mapper.RestaurantsResponseModelMapper
import io.android.ptt.remote.features.restaurants.service.RestaurantsService
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