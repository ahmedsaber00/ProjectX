package io.android.ptt.data.features.recipes.repository

import io.android.ptt.data.features.recipes.model.RecipeEntity
import io.reactivex.Flowable

interface RecipesRemote {

    fun getRecipes(): Flowable<List<RecipeEntity>>

}