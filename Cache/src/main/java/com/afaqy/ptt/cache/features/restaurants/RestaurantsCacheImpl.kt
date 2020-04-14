package com.afaqy.ptt.cache.features.restaurants

import com.afaqy.ptt.cache.AppDatabase
import com.afaqy.ptt.cache.features.restaurants.mapper.CachedRestaurantMapper
import com.afaqy.ptt.data.features.restaurants.model.RestaurantEntity
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsCache
import io.reactivex.Flowable
import javax.inject.Inject

class RestaurantsCacheImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: CachedRestaurantMapper
) : RestaurantsCache {

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        return appDatabase.cachedRestaurantDao().getRestaurants()
            .map {
                it.map { cachedRestaurant -> mapper.mapFromCached(cachedRestaurant) }
            }
    }



}