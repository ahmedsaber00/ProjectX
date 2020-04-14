package com.afaqy.ptt.data.features.recipes.store

import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import com.afaqy.ptt.data.features.recipes.repository.RecipesDataStore
import com.afaqy.ptt.data.features.recipes.repository.RecipesRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class RecipesRemoteDataStore @Inject constructor(
    private val recipesRemote: RecipesRemote
) : RecipesDataStore {

    override fun getRecipes(): Flowable<List<RecipeEntity>> {
        return recipesRemote.getRecipes()
    }

    override fun clearRecipes(): Completable {
        throw UnsupportedOperationException("Clearing Recipes isn't supported here...")
    }

    override fun saveRecipes(recipes: List<RecipeEntity>): Completable {
        throw UnsupportedOperationException("Saving Recipes isn't supported here...")
    }

    override fun setRecipeAsBookmarked(recipeId: Long): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun setRecipeAsNotBookmarked(recipeId: Long): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun getBookmarkedRecipes(): Flowable<List<RecipeEntity>> {
        throw UnsupportedOperationException("Getting Bookmarked Recipes isn't supported here...")
    }

}