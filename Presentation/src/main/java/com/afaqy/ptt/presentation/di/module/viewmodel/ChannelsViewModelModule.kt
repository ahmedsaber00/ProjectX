package com.afaqy.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.afaqy.ptt.presentation.di.ViewModelKey
import com.afaqy.ptt.presentation.features.channels.ChannelsViewModel

@Module
abstract class ChannelsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsViewModel::class)
    abstract fun bindChannelsViewModel(viewModel: ChannelsViewModel): ViewModel
}