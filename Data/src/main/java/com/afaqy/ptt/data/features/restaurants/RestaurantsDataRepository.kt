package com.afaqy.ptt.data.features.restaurants

import com.afaqy.ptt.data.features.restaurants.mapper.RestaurantMapper
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsCache
import com.afaqy.ptt.data.features.restaurants.store.RestaurantsDataStoreFactory
import com.afaqy.ptt.domain.features.restaurants.model.Restaurant
import com.afaqy.ptt.domain.features.restaurants.repository.RestaurantsRepository
import io.reactivex.Observable
import javax.inject.Inject

class RestaurantsDataRepository @Inject constructor(
    private val mapper: RestaurantMapper,
    private val cache: RestaurantsCache,
    private val factory: RestaurantsDataStoreFactory
) : RestaurantsRepository {

    override fun getRestaurants(): Observable<List<Restaurant>> {
        return factory.getDataStore().getRestaurants().toObservable()
            .map {
                it.map { entity -> mapper.mapFromEntity(entity) }
            }
    }

}