package io.android.ptt.cache.features.restaurants.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.android.ptt.cache.features.restaurants.db.RestaurantConstants.DELETE_RESTAURANTS
import io.android.ptt.cache.features.restaurants.db.RestaurantConstants.QUERY_RESTAURANTS
import io.android.ptt.cache.features.restaurants.model.CachedRestaurant
import io.reactivex.Flowable

@Dao
abstract class CachedRestaurantDao {

    @Query(QUERY_RESTAURANTS)
    @JvmSuppressWildcards
    abstract fun getRestaurants(): Flowable<List<CachedRestaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertRestaurants(restaurants: List<CachedRestaurant>)

    @Query(DELETE_RESTAURANTS)
    abstract fun deleteRestaurants()

}