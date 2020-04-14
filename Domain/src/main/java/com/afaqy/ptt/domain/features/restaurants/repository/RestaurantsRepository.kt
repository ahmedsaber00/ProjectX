package com.afaqy.ptt.domain.features.restaurants.repository

import com.afaqy.ptt.domain.features.restaurants.model.Restaurant
import io.reactivex.Observable

interface RestaurantsRepository {

    fun getRestaurants(): Observable<List<Restaurant>>
}