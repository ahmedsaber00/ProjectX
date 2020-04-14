package com.afaqy.ptt.domain.features.restaurants.interactor.browse

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.restaurants.repository.RestaurantsRepository
import com.afaqy.ptt.domain.features.restaurants.model.Restaurant
import io.reactivex.Observable
import javax.inject.Inject

open class GetRestaurants @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Restaurant>, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Restaurant>> {
        return restaurantsRepository.getRestaurants()
    }

}