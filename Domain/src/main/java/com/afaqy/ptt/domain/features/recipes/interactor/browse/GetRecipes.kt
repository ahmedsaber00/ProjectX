package com.afaqy.ptt.domain.features.recipes.interactor.browse

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.recipes.model.Recipe
import com.afaqy.ptt.domain.features.recipes.repository.RecipesRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetRecipes @Inject constructor(
    private val recipesRepository: RecipesRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Recipe>, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Recipe>> {
        return recipesRepository.getRecipes()
    }

}