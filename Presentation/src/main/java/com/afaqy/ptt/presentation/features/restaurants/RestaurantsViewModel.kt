package com.afaqy.ptt.presentation.features.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afaqy.ptt.domain.features.restaurants.interactor.browse.GetRestaurants
import com.afaqy.ptt.domain.features.restaurants.model.Restaurant
import com.afaqy.ptt.presentation.base.mapper.RestaurantViewMapper
import com.afaqy.ptt.presentation.base.model.RestaurantView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(
    private val getRestaurants: GetRestaurants?,
    private val mapper: RestaurantViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<RestaurantView>>> = MutableLiveData()

    override fun onCleared() {
        getRestaurants?.dispose()
        super.onCleared()
    }

    fun getRestaurants(): LiveData<Resource<List<RestaurantView>>> {
        return liveData
    }

    fun fetchRestaurants() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        getRestaurants?.execute(RestaurantsSubscriber())
    }

    inner class RestaurantsSubscriber : DisposableObserver<List<Restaurant>>() {

        override fun onNext(t: List<Restaurant>) {
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) },
                    null
                )
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

}