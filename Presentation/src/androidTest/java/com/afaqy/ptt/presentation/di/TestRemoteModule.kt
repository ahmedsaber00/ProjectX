package com.afaqy.ptt.presentation.di

import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import com.afaqy.ptt.data.features.recipes.repository.RecipesRemote
import com.afaqy.ptt.remote.features.recipes.service.RecipesService

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideRecipesService(): RecipesService {
        return mock()
    }

    @Provides
    @JvmStatic
    fun provideRecipesRemote(): RecipesRemote {
        return mock()
    }

}