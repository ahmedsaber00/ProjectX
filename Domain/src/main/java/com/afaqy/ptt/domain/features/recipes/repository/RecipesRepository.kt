package com.afaqy.ptt.domain.features.recipes.repository

import com.afaqy.ptt.domain.features.recipes.model.Recipe
import io.reactivex.Completable
import io.reactivex.Observable

interface RecipesRepository {

    fun getRecipes(): Observable<List<Recipe>>

    fun bookmarkRecipe(recipeId: Long): Completable

    fun unBookmarkRecipe(recipeId: Long): Completable

    fun getBookmarkedRecipes(): Observable<List<Recipe>>
}