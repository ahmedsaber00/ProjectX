package io.android.ptt.cache.features.restaurants.mapper

import io.android.ptt.cache.base.mapper.CacheMapper
import io.android.ptt.cache.features.restaurants.model.CachedRestaurant
import io.android.ptt.data.features.restaurants.model.RestaurantEntity
import javax.inject.Inject

class CachedRestaurantMapper @Inject constructor() :
    CacheMapper<CachedRestaurant, RestaurantEntity> {

    override fun mapFromCached(type: CachedRestaurant): RestaurantEntity {
        return RestaurantEntity(
            type.id, type.title, type.description,
            type.url, type.urlToImage
        )
    }

    override fun mapToCached(type: RestaurantEntity): CachedRestaurant {
        return CachedRestaurant(
            type.id, type.title, type.description,
            type.url, type.urlToImage
        )
    }

}