package io.android.ptt.cache.features.restaurants

import io.android.ptt.cache.AppDatabase
import io.android.ptt.cache.features.restaurants.mapper.CachedRestaurantMapper
import io.android.ptt.data.features.restaurants.model.RestaurantEntity
import io.android.ptt.data.features.restaurants.repository.RestaurantsCache
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