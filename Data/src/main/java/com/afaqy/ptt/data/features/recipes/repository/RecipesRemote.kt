package com.afaqy.ptt.data.features.recipes.repository

import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import io.reactivex.Flowable

interface RecipesRemote {

    fun getRecipes(): Flowable<List<RecipeEntity>>

}