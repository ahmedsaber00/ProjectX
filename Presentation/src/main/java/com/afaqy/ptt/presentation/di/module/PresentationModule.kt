package com.afaqy.ptt.presentation.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory

@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}