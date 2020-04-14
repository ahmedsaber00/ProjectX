package io.android.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.android.ptt.presentation.di.ViewModelKey
import io.android.ptt.presentation.features.bookmarked.BookmarkedRecipesViewModel

@Module
abstract class BookmarkedViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkedRecipesViewModel::class)
    abstract fun bindBookmarkedRecipesViewModel(viewModel: BookmarkedRecipesViewModel): ViewModel
}