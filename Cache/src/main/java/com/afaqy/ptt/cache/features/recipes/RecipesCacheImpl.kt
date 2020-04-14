package com.afaqy.ptt.cache.features.recipes

import com.afaqy.ptt.cache.AppDatabase
import com.afaqy.ptt.cache.features.recipes.mapper.CachedRecipeMapper
import com.afaqy.ptt.cache.features.recipes.model.Config
import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import com.afaqy.ptt.data.features.recipes.repository.RecipesCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RecipesCacheImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: CachedRecipeMapper
) : RecipesCache {

    override fun clearRecipes(): Completable {
        return Completable.defer {
            appDatabase.cachedRecipesDao().deleteRecipes()
            Completable.complete()
        }
    }

    override fun saveRecipes(recipes: List<RecipeEntity>): Completable {
        return Completable.defer {
            appDatabase.cachedRecipesDao().insertRecipes(
                recipes.map { mapper.mapToCached(it) })
            Completable.complete()
        }
    }

    override fun getRecipes(): Flowable<List<RecipeEntity>> {
        return appDatabase.cachedRecipesDao().getRecipes()
            .map {
                it.map { cachedRecipe -> mapper.mapFromCached(cachedRecipe) }
            }
    }

    override fun getBookmarkedRecipes(): Flowable<List<RecipeEntity>> {
        return appDatabase.cachedRecipesDao().getBookmarkedRecipes()
            .map {
                it.map { cachedRecipe -> mapper.mapFromCached(cachedRecipe) }
            }
    }

    override fun setRecipeAsBookmarked(recipeId: Long): Completable {
        return Completable.defer {
            appDatabase.cachedRecipesDao().setBookmarkStatus(true, recipeId)
            Completable.complete()
        }
    }

    override fun setRecipeAsNotBookmarked(recipeId: Long): Completable {
        return Completable.defer {
            appDatabase.cachedRecipesDao().setBookmarkStatus(false, recipeId)
            Completable.complete()
        }
    }

    override fun areRecipesCached(): Single<Boolean> {
        return appDatabase.cachedRecipesDao().getRecipes().isEmpty
            .map {
                !it
            }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            appDatabase.configDao().insertConfig(
                Config(
                    lastCacheTime = lastCache
                )
            )
            Completable.complete()
        }
    }

    override fun isRecipesCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return appDatabase.configDao().getConfig()
            .onErrorReturn { Config(lastCacheTime = 0) }
            .map { currentTime - it.lastCacheTime > expirationTime }
    }

}