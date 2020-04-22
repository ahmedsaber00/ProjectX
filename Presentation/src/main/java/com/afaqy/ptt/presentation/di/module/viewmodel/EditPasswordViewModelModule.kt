package com.afaqy.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.afaqy.ptt.presentation.di.ViewModelKey
import com.afaqy.ptt.presentation.features.editpassword.EditPasswordViewModel

@Module
abstract class EditPasswordViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditPasswordViewModel::class)
    abstract fun bindEditProfileViewModel(viewModel: EditPasswordViewModel): ViewModel
}