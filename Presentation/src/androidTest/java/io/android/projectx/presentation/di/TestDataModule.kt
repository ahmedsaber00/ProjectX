package io.android.projectx.presentation.di

import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import io.android.projectx.domain.features.login.repository.LoginRepository
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): LoginRepository {
        return mock()
    }

}