package com.afaqy.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.afaqy.ptt.presentation.di.ViewModelKey
import com.afaqy.ptt.presentation.features.restaurants.RestaurantsViewModel

@Module
abstract class RestaurantsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantsViewModel::class)
    abstract fun bindRestaurantsViewModel(viewModel: RestaurantsViewModel): ViewModel
}