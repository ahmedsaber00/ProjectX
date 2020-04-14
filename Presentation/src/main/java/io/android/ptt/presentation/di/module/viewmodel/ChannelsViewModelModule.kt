package io.android.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.android.ptt.presentation.di.ViewModelKey
import io.android.ptt.presentation.features.channels.ChannelsViewModel

@Module
abstract class ChannelsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsViewModel::class)
    abstract fun bindChannelsViewModel(viewModel: ChannelsViewModel): ViewModel
}