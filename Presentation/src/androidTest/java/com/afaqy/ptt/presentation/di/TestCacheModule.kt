package com.afaqy.ptt.presentation.di

import android.app.Application
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import com.afaqy.ptt.cache.AppDatabase
import com.afaqy.ptt.data.features.recipes.repository.RecipesCache

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun provideRecipesCache(): RecipesCache {
        return mock()
    }

}