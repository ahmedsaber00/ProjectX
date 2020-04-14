package com.afaqy.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.afaqy.ptt.presentation.di.ViewModelKey
import com.afaqy.ptt.presentation.features.browse.BrowseRecipesViewModel

@Module
abstract class BrowseViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseRecipesViewModel::class)
    abstract fun bindBrowseRecipesViewModel(viewModel: BrowseRecipesViewModel): ViewModel
}