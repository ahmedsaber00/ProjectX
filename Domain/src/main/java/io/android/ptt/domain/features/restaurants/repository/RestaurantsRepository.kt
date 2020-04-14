package io.android.ptt.domain.features.restaurants.repository

import io.android.ptt.domain.features.restaurants.model.Restaurant
import io.reactivex.Observable

interface RestaurantsRepository {

    fun getRestaurants(): Observable<List<Restaurant>>
}