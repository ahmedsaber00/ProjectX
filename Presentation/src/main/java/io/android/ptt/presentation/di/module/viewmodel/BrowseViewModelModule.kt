package io.android.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.android.ptt.presentation.di.ViewModelKey
import io.android.ptt.presentation.features.browse.BrowseRecipesViewModel

@Module
abstract class BrowseViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseRecipesViewModel::class)
    abstract fun bindBrowseRecipesViewModel(viewModel: BrowseRecipesViewModel): ViewModel
}