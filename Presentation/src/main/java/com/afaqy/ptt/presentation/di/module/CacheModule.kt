package com.afaqy.ptt.presentation.di.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.afaqy.ptt.cache.features.recipes.RecipesCacheImpl
import com.afaqy.ptt.cache.AppDatabase
import com.afaqy.ptt.cache.features.login.LoginCacheImpl
import com.afaqy.ptt.cache.features.restaurants.RestaurantsCacheImpl
import com.afaqy.ptt.data.features.login.repository.LoginCache
import com.afaqy.ptt.data.features.recipes.repository.RecipesCache
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsCache

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): AppDatabase {
            return AppDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindLoginCache(loginCache: LoginCacheImpl): LoginCache

    @Binds
    abstract fun bindRecipesCache(recipesCache: RecipesCacheImpl): RecipesCache

    @Binds
    abstract fun bindRestaurantsCache(restaurantsCache: RestaurantsCacheImpl): RestaurantsCache
}