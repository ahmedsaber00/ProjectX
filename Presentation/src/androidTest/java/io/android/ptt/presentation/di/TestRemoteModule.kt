package io.android.ptt.presentation.di

import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import io.android.ptt.data.features.recipes.repository.RecipesRemote
import io.android.ptt.remote.features.recipes.service.RecipesService

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