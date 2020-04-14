package com.afaqy.ptt.presentation.features.bookmarked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afaqy.ptt.domain.features.recipes.interactor.bookmarked.GetBookmarkedRecipes
import com.afaqy.ptt.domain.features.recipes.model.Recipe
import com.afaqy.ptt.presentation.base.mapper.RecipeViewMapper
import com.afaqy.ptt.presentation.base.model.RecipeView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BookmarkedRecipesViewModel @Inject constructor(
    private val getBookmarkedRecipes: GetBookmarkedRecipes,
    private val mapper: RecipeViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<RecipeView>>> = MutableLiveData()

    override fun onCleared() {
        getBookmarkedRecipes.dispose()
        super.onCleared()
    }

    fun getBookmarkedRecipes(): LiveData<Resource<List<RecipeView>>> {
        return liveData
    }

    fun fetchBookmarkedRecipes() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBookmarkedRecipes.execute(RecipesSubscriber())
    }

    inner class RecipesSubscriber : DisposableObserver<List<Recipe>>() {

        override fun onNext(t: List<Recipe>) {
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) },
                    null
                )
            )
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

        override fun onComplete() {}

    }

}