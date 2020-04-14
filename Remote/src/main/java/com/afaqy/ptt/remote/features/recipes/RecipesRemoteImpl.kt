package com.afaqy.ptt.remote.features.recipes

import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import com.afaqy.ptt.data.features.recipes.repository.RecipesRemote
import com.afaqy.ptt.remote.features.recipes.mapper.RecipesResponseModelMapper
import com.afaqy.ptt.remote.features.recipes.service.RecipesService
import io.reactivex.Flowable
import javax.inject.Inject

class RecipesRemoteImpl @Inject constructor(
    private val service: RecipesService,
    private val mapper: RecipesResponseModelMapper
) : RecipesRemote {

    override fun getRecipes(): Flowable<List<RecipeEntity>> {
        return service.searchRecipes(1)
            .map {
                it.items.map { model -> mapper.mapFromModel(model) }
            }
    }

}