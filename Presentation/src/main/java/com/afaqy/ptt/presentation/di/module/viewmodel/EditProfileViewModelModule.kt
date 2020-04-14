package com.afaqy.ptt.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.afaqy.ptt.presentation.di.ViewModelKey
import com.afaqy.ptt.presentation.features.editprofile.EditProfileViewModel

@Module
abstract class EditProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun bindEditProfileViewModel(viewModel: EditProfileViewModel): ViewModel
}