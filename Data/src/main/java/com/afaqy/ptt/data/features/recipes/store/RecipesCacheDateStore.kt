package com.afaqy.ptt.data.features.recipes.store

import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import com.afaqy.ptt.data.features.recipes.repository.RecipesCache
import com.afaqy.ptt.data.features.recipes.repository.RecipesDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class RecipesCacheDateStore @Inject constructor(
    private val recipesCache: RecipesCache
) : RecipesDataStore {

    override fun getRecipes(): Flowable<List<RecipeEntity>> {
        return recipesCache.getRecipes()
    }

    override fun clearRecipes(): Completable {
        return recipesCache.clearRecipes()
    }

    override fun saveRecipes(recipes: List<RecipeEntity>): Completable {
        return recipesCache.saveRecipes(recipes)
            .andThen(recipesCache.setLastCacheTime(System.currentTimeMillis()))
    }

    override fun setRecipeAsBookmarked(recipeId: Long): Completable {
        return recipesCache.setRecipeAsBookmarked(recipeId)
    }

    override fun setRecipeAsNotBookmarked(recipeId: Long): Completable {
        return recipesCache.setRecipeAsNotBookmarked(recipeId)
    }

    override fun getBookmarkedRecipes(): Flowable<List<RecipeEntity>> {
        return recipesCache.getBookmarkedRecipes()
    }

}