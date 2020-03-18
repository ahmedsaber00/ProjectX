package io.android.projectx.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.android.projectx.presentation.di.ViewModelKey
import io.android.projectx.presentation.features.channels.ChannelsViewModel

@Module
abstract class ChannelsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsViewModel::class)
    abstract fun bindChannelsViewModel(viewModel: ChannelsViewModel): ViewModel
}