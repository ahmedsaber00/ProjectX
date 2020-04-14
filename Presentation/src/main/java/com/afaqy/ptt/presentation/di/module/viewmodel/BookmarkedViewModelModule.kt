package com.afaqy.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.afaqy.ptt.presentation.di.ViewModelKey
import com.afaqy.ptt.presentation.features.bookmarked.BookmarkedRecipesViewModel

@Module
abstract class BookmarkedViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkedRecipesViewModel::class)
    abstract fun bindBookmarkedRecipesViewModel(viewModel: BookmarkedRecipesViewModel): ViewModel
}